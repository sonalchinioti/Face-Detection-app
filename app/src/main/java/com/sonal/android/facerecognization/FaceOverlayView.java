package com.sonal.android.facerecognization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by User on 21-06-2016.
 */
public class FaceOverlayView extends View {


    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        FaceDetector mFaceDetector = new FaceDetector.Builder(getContext()).
                                            setMode(FaceDetector.FAST_MODE).
                                            setLandmarkType(FaceDetector.ALL_LANDMARKS).
                                            setTrackingEnabled(false).build();

        if(mFaceDetector.isOperational()){
            Frame frame = new Frame.Builder().setBitmap(mBitmap).build();
            mFaces = mFaceDetector.detect(frame);
            mFaceDetector.release();
            invalidate();

        }else{
            Toast.makeText(getContext(),"Sorry try after some time ",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            drawFaceBox(canvas, scale);
        }
    }
    private double drawBitmap( Canvas canvas ) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect( 0, 0, (int) ( imageWidth * scale ), (int) ( imageHeight * scale ) );
        canvas.drawBitmap( mBitmap, null, destBounds, null );
        return scale;

    }
    private void drawFaceBox(Canvas canvas, double scale) {

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for(int i = 0; i < mFaces.size(); i++ ){
            Face face = mFaces.valueAt(i);
            left = (float) (face.getPosition().x*scale);
            top = (float) ( face.getPosition().y*scale );
            right = (float) scale * ( face.getPosition().x + face.getWidth() );
            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );

            canvas.drawRect( left, top, right, bottom, paint );



        }
    }
}
