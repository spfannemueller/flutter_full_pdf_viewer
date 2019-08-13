package com.alveliu.flutterfullpdfviewer;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * FlutterFullPdfViewerManager
 */
class FlutterFullPdfViewerManager {

    boolean closed = false;
    PDFView pdfView;
    Activity activity;

    FlutterFullPdfViewerManager (final Activity activity) {
        this.pdfView = new PDFView(activity, null);
        this.activity = activity;

    }

    void openPDF(String path) {

        pdfView.setBackgroundColor(Color.parseColor("#4d4d4d"));



        File file = new File(path);
        pdfView.fromFile(file)
                .spacing(30)
                .pageFitPolicy(FitPolicy.BOTH)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();
    }

    void resize(FrameLayout.LayoutParams params) {
        pdfView.setLayoutParams(params);

    }

    void close(MethodCall call, MethodChannel.Result result) {
        if (pdfView != null) {
            ViewGroup vg = (ViewGroup) (pdfView.getParent());
            vg.removeView(pdfView);
        }
        pdfView = null;
        if (result != null) {
            result.success(null);
        }

        closed = true;
        FlutterFullPdfViewerPlugin.channel.invokeMethod("onDestroy", null);
    }

    void close() {
        close(null, null);
    }


}