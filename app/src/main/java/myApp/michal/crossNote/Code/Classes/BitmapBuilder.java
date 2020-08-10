package myApp.michal.crossNote.Code.Classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import androidx.core.content.res.ResourcesCompat;

import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Code.Helper;
import myApp.michal.crossNote.Databases.DbHelper;

public class BitmapBuilder {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Context context;
    private TrainingBuilder trainingBuilder;

    public BitmapBuilder(Context context, int position, DbHelper dbHelper){
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bitmap = Bitmap.createBitmap(
                    600,
                    800,
                    Bitmap.Config.RGBA_F16);
        } else {
            bitmap = Bitmap.createBitmap(
                    600,
                    800,
                    Bitmap.Config.ARGB_8888);
        }
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        paint = new Paint();
        trainingBuilder = dbHelper.getAll().get(position);
        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.background),0,0,paint);
    }

    public Bitmap buildText(){
        canvas.drawText(
                getHeader(),
                canvas.getWidth()/2f,
                100 , // y
                getPaint(Typeface.BOLD));
        int finalHeight = 200;
        for(int i = 0; i < trainingBuilder.getMotionList().size(); i++){
            canvas.drawText(getLine(i),
                    canvas.getWidth()/2f,
                    190f + i*70,
                    getPaint(Typeface.NORMAL)
            );
            finalHeight += 70;
        }
        canvas.drawText(
                Helper.getResultString(trainingBuilder, trainingBuilder.getResult()),
                canvas.getWidth()/2f,
                finalHeight += 20,
                getPaint(Typeface.NORMAL));

        bitmap.setHeight(finalHeight + 70);
        return bitmap;
    }

    private String getLine(int position){
        String s;
        List<MotionBuilder> motionBuilders = trainingBuilder.getMotionList();
        String rep = motionBuilders.get(position).getRepetition();
        EMotionNames EMotionNames = motionBuilders.get(position).getEMotionNames();
        String motion = EMotionNames.toString().replace("_", " ");
        String weight = motionBuilders.get(position).getWeight();
        boolean status = motionBuilders.get(position).isStatus();

        if(motion.equals(EMotionNames.EMPTY.toString())){
            s = rep;
            return s;
        } else if((rep == null || rep.length() == 0) && (weight == null || weight.length() == 0)) {
            s = " " + " " + motion + " " + " ";
            return s;
        } else if(weight == null || weight.length() == 0){
            s = rep + " " + motion + " ";
            if(EMotionNames == EMotionNames.Airbike || EMotionNames == EMotionNames.Row || EMotionNames == EMotionNames.Run){
                s += status ? "cal" : "m";
                return s;
            }
            return s;
        } else if(rep == null || rep.length() == 0){
            s = " " + " " + motion + " " + weight + " kg";
            return s;
        }else {
            s = rep + " " + motion + " " + weight + " kg";
            return s;
        }
    }

    private String getHeader(){
        StringBuilder stringBuilder = new StringBuilder();
        EWorkoutNames name = trainingBuilder.getEWorkoutNames();
        String time = trainingBuilder.getTime();
        String header;

        switch (name){
            case Benchmark: {
                header = stringBuilder.append(name.name()).append(" ").append(time).toString();
                break;
            }
            case RFT: {
                header = stringBuilder.append(name.name()).append(" ").append(time).append(" rounds").toString();
                break;
            }
            default: {
                header = stringBuilder.append(name.name()).append(" ").append(time).append(" minutes").toString();
            }
        }
        return header;
    }

    private Typeface createTypeface(int style){
        return Typeface.create(ResourcesCompat.getFont(context, R.font.caviardreams), style);
    }

    private Paint getPaint(int style){
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(35);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(createTypeface(style));
        return paint;
    }

}


