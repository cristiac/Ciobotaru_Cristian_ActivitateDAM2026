package com.example.seminar04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImagineAdapter extends ArrayAdapter<ImaginiCurcani> {
    private final Context context;
    private final int resource;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public ImagineAdapter(@NonNull Context context, int resource, @NonNull List<ImaginiCurcani> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.executorService = Executors.newFixedThreadPool(4);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ImaginiCurcani item = getItem(position);

        if (item != null) {
            TextView tvTitlu = view.findViewById(R.id.tvTitluImagine);
            TextView tvDescriere = view.findViewById(R.id.tvDescriereImagine);
            ImageView ivCurcan = view.findViewById(R.id.ivCurcan);

            tvTitlu.setText(item.getTitlu());
            tvDescriere.setText(item.getDescriere());

            ivCurcan.setTag(item.getUrlImagine());

            int drawableId = getLocalDrawableId(position);
            if (drawableId != 0) {
                executorService.execute(() -> {
                    Bitmap sample = decodeSampledBitmapFromResource(drawableId, 200, 200);
                    if (sample != null) {
                        mainHandler.post(() -> ivCurcan.setImageBitmap(sample));
                    }
                });
            }

            if (item.getUrlImagine() != null && !item.getUrlImagine().isEmpty()) {
                informaImagineFromUrl(item.getUrlImagine(), ivCurcan);
            }
        }

        return view;
    }

    private int getLocalDrawableId(int position) {
        switch (position) {
            case 0:
                return R.drawable.curcan_alb;
            case 1:
                return R.drawable.curcan_bronzat;
            case 2:
                return R.drawable.curcan_rosu;
            case 3:
                return R.drawable.curcan_salbatic;
            case 4:
                return R.drawable.curcan_olandez;
            default:
                return 0;
        }
    }

    private Bitmap decodeSampledBitmapFromResource(int resId, int reqWidth, int reqHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resId, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(context.getResources(), resId, options);
        } catch (Exception e) {
            return null;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void informaImagineFromUrl(String urlString, ImageView imageView) {
        executorService.execute(() -> {
            Bitmap bitmap = downloadBitmap(urlString);
            if (bitmap != null) {
                mainHandler.post(() -> {
                    if (urlString.equals(imageView.getTag())) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private Bitmap downloadBitmap(String urlStr) {
        String currentUrl = urlStr;
        for (int redirects = 0; redirects < 5; redirects++) {
            try {
                URL url = new URL(currentUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestProperty("User-Agent", "WikimediaBot/1.0 (https://wikimedia.org; bot@wikimedia.org)");
                conn.setRequestProperty("Accept", "image/webp,image/apng,image/png,image/jpeg,image/*,*/*");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
                    responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                    responseCode == 307 || responseCode == 308) {
                    String loc = conn.getHeaderField("Location");
                    if (loc != null && !loc.isEmpty()) {
                        currentUrl = loc;
                        continue;
                    }
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = conn.getInputStream();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    byte[] bytes = out.toByteArray();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
