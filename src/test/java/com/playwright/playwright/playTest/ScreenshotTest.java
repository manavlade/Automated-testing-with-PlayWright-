package com.playwright.playwright.playTest;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.Media;
import com.playwright.playwright.base.BaseTest;

public class ScreenshotTest extends BaseTest {
    @Test
    void test() {
        page.navigate("http://localhost:4200/");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.evaluate("() => {" +
                "  if (typeof echarts !== 'undefined') {" +
                "    const chartEl = document.getElementById('mainChart');" +
                "    if (chartEl) {" +
                "      const chartInstance = echarts.getInstanceByDom(chartEl);" +
                "      if (chartInstance) {" +
                "        chartInstance.dispatchAction({ type: 'showTip', seriesIndex: 0, dataIndex: 2 });" +
                "      }" +
                "    }" +
                "  }" +
                "}");

        page.waitForTimeout(2000);

        page.emulateMedia(new Page.EmulateMediaOptions().setMedia(Media.PRINT));
        
        page.waitForTimeout(500);

        page.pdf(new Page.PdfOptions()
                .setPath(Paths.get("report.pdf"))
                .setFormat("A4")
                .setPrintBackground(true) // Ensures colors and charts show up
                .setPreferCSSPageSize(true) // Use CSS page sizing
                .setMargin(new Margin().setTop("10mm").setBottom("10mm").setLeft("10mm").setRight("10mm")));

    }
}