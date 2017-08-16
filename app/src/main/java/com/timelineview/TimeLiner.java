package com.timelineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Time Liner View. Please check sample screenshot for more example
 */
public class TimeLiner extends View {

    private int lineColor;
    private boolean firstElement;
    private float lineWidth;
    private boolean lastElement;
    private float circle_radius;
    private int circleColor;
    private float leftPadding;
    private float topPadding;
    private Paint paint;

    public TimeLiner(Context context) {
        super(context);
        init(null);
    }

    public TimeLiner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TimeLiner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private boolean isLastElement() {
        return lastElement;
    }

    public void setLastElement() {
        this.lastElement = true;
    }

    private boolean isFirstElement() {
        return firstElement;
    }

    public void setFirstElement() {
        this.firstElement = true;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.timeLiner);
        lineColor = typedArray.getColor(R.styleable.timeLiner_lineColor, Color.RED);
        firstElement = typedArray.getBoolean(R.styleable.timeLiner_firstElement, false);
        lineWidth = typedArray.getDimension(R.styleable.timeLiner_lineWidth, 3);
        circle_radius = typedArray.getDimension(R.styleable.timeLiner_circleRadius, 10);
        circleColor = typedArray.getColor(R.styleable.timeLiner_circleColor, Color.RED);
        leftPadding = typedArray.getDimension(R.styleable.timeLiner_leftPadding, 10);
        topPadding = typedArray.getDimension(R.styleable.timeLiner_topPadding, 10);
        typedArray.recycle();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float contentHeight = getHeight();



        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(circleColor);
        canvas.drawCircle(leftPadding, topPadding, circle_radius, paint);


        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);

        //Top Line
        if (!isFirstElement()) {
            canvas.drawLine(leftPadding, 0, leftPadding, topPadding - circle_radius, paint);
        }

        //BottomLine
        if (!isLastElement()) {
            canvas.drawLine(leftPadding, topPadding + circle_radius, leftPadding, contentHeight, paint);
        }
    }


}
