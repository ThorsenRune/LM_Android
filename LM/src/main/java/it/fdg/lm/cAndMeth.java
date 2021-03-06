//171222
/// //folder: https://drive.google.com/drive/u/0/folders/0B5pCUAt6BabuU1I1Ri1zNzA4SG8
//Doc: https://docs.google.com/document/d/1F2RN7d3CpUOBJBWO0P_yZ6IOQa-Yb-6Kb_5WM3j-_20/edit

/*
* Refactored for version 2 RT170525
*
* */

package it.fdg.lm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static it.fdg.lm.cKonst.nTextSize;
import static it.fdg.lm.cProgram3.bDesignMode;
import static it.fdg.lm.cProgram3.mErrMsg;
import static it.fdg.lm.cProgram3.mMsgDebug;


/**
 * Created by rthorsen on 18/05/2017.
 */
public class cAndMeth extends Activity{
    private static  Context mContext;
    public static Paint oTextPaint= new Paint();                               //General text format
    private static boolean isBlockedScrollView=true;                           //Enable/Block scrollviews

    public static void mInit(Context main2) {
        mContext=main2;
    }



    //              DATE
    public static String mGetDateStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(" dd-MMM-yyyy HH:mm");
        return df.format(c.getTime());
    }



    //get a 2 point array with minimum and maximum of signal aValues
    public static int[] mGetRange(int[] aValues,int[] aExtremi){
        int maxVal=aValues[0];
        int minVal=aValues[0];
        for (int i=1;i< aValues.length;i++){
            if(minVal>aValues[i]){minVal=aValues[i];}
            if(maxVal<aValues[i]){maxVal=aValues[i];}
        }
        aExtremi[0]=minVal;
        aExtremi[1]=maxVal;
        return  aExtremi;
    }


    //*     Set Get the percentual heigh (weitgh of a layout)
    public static float mLayoutHeightGet(ViewGroup myPane  ) {
        ViewGroup.LayoutParams lp = myPane.getLayoutParams();
        float w = ((LinearLayout.LayoutParams) lp).weight;
        return w;
    }
    public static void mLayoutWeightSet(ViewGroup myPane, float w) {    //Set relative size or hide if negative
        mSetVisibility(myPane,w>0);
        if (w>1){
            ViewGroup.LayoutParams lp = myPane.getLayoutParams();
            ((LinearLayout.LayoutParams) lp).weight = w;
            myPane.setLayoutParams(lp);
        }
    }
    public static void mSleep(int i) {          //Stop the thread for some time
        try{ Thread.sleep(i);
        }catch( InterruptedException e){
            mMsgDebug("Cant sleep 170929" +Thread.currentThread().getName());
        }
        if (i>199)
            mMsgDebug("Long sleep");
    }

    //      Android controls
    public static boolean mIsHidden(View v){
        return v.getVisibility()!=View.VISIBLE;
    }

    public static void mToggleVisibility(View view) {
         if (view.getVisibility()== View.VISIBLE)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
    }
    public static void mSetVisibility(View w,int nTriState) {  //170822
        //0: not there,  1: visible , 2 invisible placeholder
        if (bDesignMode())
            w.setVisibility(View.VISIBLE);
        else if (nTriState==0)
            w.setVisibility(View.GONE);
        else if (nTriState==1)
            w.setVisibility( View.VISIBLE);
        else if (nTriState==2)
            w.setVisibility(View.INVISIBLE);            //A Placeholder
        else
            w.setVisibility(View.GONE);
    }
    public static void mSetVisibility(View w,boolean nTriState) {  //170822
        //0: not there,  1: visible , 2 invisible placeholder
        if (bDesignMode())
            w.setVisibility(View.VISIBLE);
        else if (nTriState==true)
            w.setVisibility( View.VISIBLE);
        else
            w.setVisibility(View.GONE);
    }
    public static void mScrollViewBlock(View scrollView) {//170905  Testing method of enabling scrollview by intecepting keyupress
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isBlockedScrollView;     //if true then the scrollview will be blocked
            }
        });
    }

    private void mCloneView(cSliderView view, int nCount) {            //170914  Clone a view placing it next to the original
        //See AndroidSnippet170915
        ViewGroup oParent;
        oParent = (ViewGroup) view.getParent();
        for (int i = 0; i < nCount; i++) {
            cSliderView newView = new cSliderView(mContext);
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            newView.setLayoutParams(lp);
            oParent.addView(newView);
        }
    }

    public static ArrayList<View> mAllChildViews(ArrayList<View> nV, ViewGroup rootView) {
        if (nV==null )          //Give null as first parameter to initialize this array
            nV=new ArrayList<View>();
        int n = rootView.getChildCount();
        for (int i = 0; i < n; i++) {
            final View child = rootView.getChildAt(i);
            if (child instanceof ViewGroup){
                nV= mAllChildViews(nV,(ViewGroup)child);
            }
            if (child instanceof View) {
                nV.add(child);
            }
        }
        return nV;
    }

    public static boolean mMatchChildVisibility(ViewGroup rootView){    //Todo move to codesnippets
        boolean isVisible=false;
        int n = rootView.getChildCount();
        for (int i = 0; i < n; i++) {
            if (rootView.getChildAt(i) instanceof ViewGroup)
                if (mMatchChildVisibility((ViewGroup) rootView.getChildAt(i)))
                    isVisible=true;
            if (rootView.getChildAt(i).getVisibility()==View.VISIBLE)
                isVisible=true;
        }
        if (isVisible)
            rootView.setVisibility(View.VISIBLE);
        else
            rootView.setVisibility(View.GONE);
        return isVisible;
    }


    public static void mCombo_SetDataObjSel(Spinner cbMyCombo, String[] sVarList, String sSelected){
        //populate cbMyCombo       Set combo list to project variables
        Context ctx=cbMyCombo.getContext();
        ArrayAdapter<String> adapter1=mMakeSpinnerAdapter(ctx,sVarList);
        cbMyCombo.setAdapter(adapter1);
        mDropDownSetSelection(cbMyCombo,sSelected);
    }

    public static ArrayAdapter mMakeSpinnerAdapter(Context ctx, String[] sVarList) {   //Helper for mCombo_SetDataObjSel
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, sVarList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {         //The current selection element before dropdown
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                mTextViewSetTextSize(textView);
                textView.setGravity(Gravity.RIGHT);
                return view;
            }
            int i=0;
            public View getDropDownView(int position, View convertView, ViewGroup parent) {//The elements in the dropdown list
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView=(TextView) view;
                if (1==((i++) % 2))  textView.setTextColor(Color.YELLOW);
                else textView.setTextColor(Color.WHITE);
                mTextViewSetTextSize(textView);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                textView.setBackgroundColor(Color.BLACK);
                return textView;
            }
        } ;
        return adapter;
    }

    public static void mTextViewSetTextSize(TextView textView) {        //Ref171018
        if (nTextSize==0) nTextSize= (int) textView.getTextSize();  //Get the textsize in pixels
    //    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, nTextSize);
    }

    public static void mDropDownSetSelection(Spinner spnr, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            String s=adapter.getItem(position);
            if (s==null) return;
            boolean found=cFunk.mTextLike(s,value);
            if(found) {
                spnr.setSelection(position);
                return;
            }
        }
        spnr.setSelection(0);
    }

    public void cmdNewValue(View view) {
        mErrMsg("cmdNewValue");

    }

    public void cmdCangeElement(View view) {
        mErrMsg("cmdCangeElement");
    }
    //................................ANDROID  CONVERSIONS.............................
    public static void mSetTextSizeMM(Context c,Paint textPaint,float nFontSizeMM) {
        //Set the text font size in mm
        float scaledSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM,
                nFontSizeMM, c.getResources().getDisplayMetrics());
        textPaint.setTextSize(scaledSizeInPixels);       // Set sValueText size.
    }
    public static float mSizeMM2Pixels(Context c, float nFontSizeMM) {
        //Set the text font size in mm
        float scaledSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM,
                nFontSizeMM, c.getResources().getDisplayMetrics());
        return scaledSizeInPixels;       // Set sValueText size.
    }

    public static int mGetBackgroundColor(View v) {
        ColorDrawable vb1 = (ColorDrawable) v.getBackground();
        if (vb1==null) return 0;
        int c1 = vb1.getColor();
        return c1;
    }

    public static void mTextEdit_SetFocusable(EditText txtEdit) {   //180329 text edit setup
        txtEdit.setShowSoftInputOnFocus(true);  //Make the keyboard pop up on focus
        txtEdit.setHighlightColor(Color.BLACK);
        txtEdit.setFocusableInTouchMode(true);
        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if (hasFocus) {
                    ((EditText) v).selectAll();
//                    ((EditText) v).performLongClick();
                }
            }
        });

    }
}



