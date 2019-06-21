package com.ggpi.laguilde.tools;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.Calendar;


// https://developer.android.com/topic/performance/graphics/cache-bitmap.html

public class CacheUtils {

    private LruCache<String, Bitmap> memoryCache;

    /*
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
    int imageHeight = options.outHeight;
    int imageWidth = options.outWidth;
    String imageType = options.outMimeType;
    */

    /* Compute sample size */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /*
    imageView.setImageBitmap(
    decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
    */


    /*
    // Dir sample
    File directory;
if (filename.isEmpty()) {
        directory = getFilesDir();
    }
else {
        directory = getDir(filename, MODE_PRIVATE);
    }
    File[] files = directory.listFiles();

    // Input sample
    FileInputStream fis = openFileInput(filename);
    Scanner scanner = new Scanner(fis);
scanner.useDelimiter("\\Z");
    String content = scanner.next();
scanner.close();

    // Output sample
    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
fos.write(internalStorageBinding.saveFileEditText.getText().toString().getBytes());
fos.close();
*/



//    private LruCache<String, Bitmap> memoryCache;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //...
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    //...
    }
    */

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView mImageView) {
        final String imageKey = String.valueOf(resId);
/*
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.drawable.image_placeholder);
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
            task.execute(resId);
        }
        */
    }

    /*
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    ...
        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(
                    getResources(), params[0], 100, 100));
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }
    //...
    }
    */



    /* */
    /*
    Calendar cal = Calendar.getInstance();
    Intent intent = new Intent(Intent.ACTION_EDIT);
intent.setType("vnd.android.cursor.item/event");
intent.putExtra("beginTime", cal.getTimeInMillis());
intent.putExtra("allDay", true);
intent.putExtra("rrule", "FREQ=YEARLY");
intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
intent.putExtra("title", "A Test Event from android app");
    startActivity(intent);
    */


    public void addReminder(int statrYear, int startMonth, int startDay, int startHour, int startMinut, int endYear, int endMonth, int endDay, int endHour, int endMinuts){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(statrYear, startMonth, startDay, startHour, startMinut);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth, endDay, endHour, endMinuts);
        long endMillis = endTime.getTimeInMillis();

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        eventValues.put(CalendarContract.Events.TITLE, "OCS");
        eventValues.put(CalendarContract.Events.DESCRIPTION, "Clinic App");
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Nasik");
        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
        eventValues.put(CalendarContract.Events.DTEND, endMillis);

        eventValues.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=2;UNTIL="+endMillis);
        eventValues.put("eventStatus", 1);
        eventValues.put("visibility", 3);
        eventValues.put("transparency", 0);
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);

       // Uri eventUri = getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        // long eventID = Long.parseLong(eventUri.getLastPathSegment());

        /***************** Event: Reminder(with alert) Adding reminder to event *******************/

        String reminderUriString = "content://com.android.calendar/reminders";

        ContentValues reminderValues = new ContentValues();

       // reminderValues.put("event_id", eventID);
        reminderValues.put("minutes", 1);
        reminderValues.put("method", 1);

     //   Uri reminderUri = getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
    }
}
