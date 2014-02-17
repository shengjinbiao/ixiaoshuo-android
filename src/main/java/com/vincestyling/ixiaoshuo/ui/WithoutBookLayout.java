package com.vincestyling.ixiaoshuo.ui;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.vincestyling.ixiaoshuo.R;

import java.util.ArrayList;
import java.util.List;

public class WithoutBookLayout extends LinearLayout {
	public WithoutBookLayout(Context context) {
		super(context);
		setWillNotDraw(false);
	}

	public WithoutBookLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas drawCanvas = new Canvas(bitmap);

		Drawable bgDrawable = getResources().getDrawable(R.drawable.book_shelf_without_book_bg);
		bgDrawable.setBounds(0, 0, getWidth(), getHeight());
		bgDrawable.draw(drawCanvas);

		List<int[]> transparentPixels = new ArrayList<int[]>(350);
		for (int y = 0; y < bitmap.getHeight(); y++) {				// top
			detectTransparentPixels(bitmap, transparentPixels, y);
		}
		for (int y = bitmap.getHeight() - 1; y > -1; y--) {			// bottom
			detectTransparentPixels(bitmap, transparentPixels, y);
		}

		BitmapDrawable stripeDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.book_shelf_without_book_stripe);
		stripeDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		stripeDrawable.setBounds(0, 0, getWidth(), getHeight());
		stripeDrawable.draw(drawCanvas);

		bgDrawable.draw(drawCanvas);

		for (int[] pixel : transparentPixels) {
			bitmap.setPixel(pixel[0], pixel[1], Color.TRANSPARENT);
		}

		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	private void detectTransparentPixels(Bitmap bitmap, List<int[]> transparentPixels, int y) {
		// left corner
		for (int x = 0; x < bitmap.getWidth(); x++) {
			if (bitmap.getPixel(x, y) == Color.TRANSPARENT) {
				transparentPixels.add(new int[]{x, y});
			} else break;
		}

		// right corner
		for (int x = bitmap.getWidth() - 1; x > -1; x--) {
			if (bitmap.getPixel(x, y) == Color.TRANSPARENT) {
				transparentPixels.add(new int[]{x, y});
			} else break;
		}
	}

}