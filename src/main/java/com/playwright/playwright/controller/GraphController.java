package com.playwright.playwright.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Margin;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "*", allowCredentials = "true")
public class GraphController {

@GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam(defaultValue = "0") int seriesIndex,
            @RequestParam(defaultValue = "2") int dataIndex) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true));

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(1440, 900) 
            );

            Page page = context.newPage();

            page.navigate("http://localhost:4200");
            page.waitForSelector("#mainChart canvas");
            page.waitForTimeout(1500);
            page.evaluate("""
                        const allElements = document.querySelectorAll('*');
                        allElements.forEach(el => {
                            const computed = window.getComputedStyle(el);
                            const isHidden =
                                computed.display === 'none' ||
                                computed.visibility === 'hidden' ||
                                (parseFloat(computed.maxHeight) === 0
                                    && computed.overflow === 'hidden') ||
                                (parseFloat(computed.height) === 0
                                    && computed.overflow === 'hidden');
                            if (isHidden && el.tagName !== 'INPUT') {
                                el.style.maxHeight = 'none';
                                el.style.height = 'auto';
                                el.style.overflow = 'visible';
                                el.style.display = computed.display === 'none'
                                    ? 'block' : computed.display;
                                el.style.visibility = 'visible';
                            }
                        });
                    """);

            page.waitForTimeout(300);
            page.evaluate(String.format("""
                        const chartDom = document.getElementById('mainChart');
                        if (chartDom) {
                            const chartInstance = echarts.getInstanceByDom(chartDom);
                            if (chartInstance) {
                                chartInstance.dispatchAction({
                                    type: 'showTip',
                                    seriesIndex: %d,
                                    dataIndex: %d
                                });
                            }
                        }
                    """, seriesIndex, dataIndex));

            page.waitForTimeout(500);
            byte[] pdf = page.pdf(new Page.PdfOptions()
                    .setFormat("A4")
                    .setPrintBackground(true)
                    .setMargin(new Margin()
                            .setTop("10mm")
                            .setBottom("10mm")
                            .setLeft("10mm")
                            .setRight("10mm")));

            context.close();
            browser.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=home.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
