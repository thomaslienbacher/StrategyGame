package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Used to encapsulate texture blurring functions
 *
 * @author Thomas Lienbacher
 */
public class Blurring {
    
    private static final int NUM_THREADS = 4;

    private static abstract class PrepareRunnable implements Runnable {
        public Pixmap pixmap;
        public Color[][] pixels;
        public int xstart;
        public int xend;

        public PrepareRunnable(Pixmap pixmap, Color[][] pixels, int xstart, int xend) {
            this.pixmap = pixmap;
            this.pixels = pixels;
            this.xstart = xstart;
            this.xend = xend;
        }
    }
    
    private static abstract class HorizontalRunnable implements Runnable {
        public Color[][] pixels;
        public Color[][] horiPixels;
        public int xstart;
        public int xend;

        public HorizontalRunnable(Color[][] pixels, Color[][] horiPixels, int xstart, int xend) {
            this.pixels = pixels;
            this.horiPixels = horiPixels;
            this.xstart = xstart;
            this.xend = xend;
        }
    }

    private static abstract class VerticalRunnable implements Runnable {
        public Color[][] horiPixels;
        public Pixmap pixmap;
        public int xstart;
        public int xend;

        public VerticalRunnable(Color[][] horiPixels, Pixmap pixmap, int xstart, int xend) {
            this.horiPixels = horiPixels;
            this.pixmap = pixmap;
            this.xstart = xstart;
            this.xend = xend;
        }
    }
    
    public static Texture blurTexture(Pixmap pixmap) {
        //final float[] kernel = {0.06136f, 0.24477f, 0.38774f, 0.24477f, 0.06136f};
        final float[] kernel = {0.035822f, 0.05879f, 0.086425f, 0.113806f, 0.13424f, 0.141836f, 0.13424f, 0.113806f, 0.086425f, 0.05879f, 0.035822f};
        final int kw = kernel.length / 2;
        final int HEIGHT = pixmap.getHeight();
        final int WIDTH = pixmap.getWidth();
        final int[] offsets = new int[NUM_THREADS+1];

        int left = pixmap.getWidth();
        for(int i = 0; i < NUM_THREADS; i++) {
            offsets[i] = (WIDTH / NUM_THREADS) * i;
            left -= WIDTH / NUM_THREADS;
        }
        offsets[NUM_THREADS-1] += left;
        offsets[NUM_THREADS] = WIDTH;

        Color[][] pixels = new Color[WIDTH][HEIGHT];
        Color[][] horiPixels = new Color[WIDTH][HEIGHT];
        

        //fill in pixels
        Thread[] prepareThreads = new Thread[NUM_THREADS];
        for(int i = 0; i < NUM_THREADS; i++) {
            prepareThreads[i] = new Thread(new PrepareRunnable(pixmap, pixels, offsets[i], offsets[i+1]) {
                @Override
                public void run() {
                    for(int x = this.xstart; x < this.xend; x++) {
                        for(int y = 0; y < HEIGHT; y++) {
                            this.pixels[x][y] = new Color(this.pixmap.getPixel(x, y));
                        }
                    }
                }
            });
            prepareThreads[i].start();
        }

        for(int i = 0; i < NUM_THREADS; i++) {
            try {
                prepareThreads[i].join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //horizontal blur
        Thread[] horizontalThreads = new Thread[NUM_THREADS];
        for(int i = 0; i < NUM_THREADS; i++) {
            horizontalThreads[i] = new Thread(new HorizontalRunnable(pixels, horiPixels, offsets[i], offsets[i+1]) {
                @Override
                public void run() {
                    for(int x = this.xstart; x < this.xend; x++) {
                        for(int y = 0; y < HEIGHT; y++) {
                            Color c = new Color();
                            Color tmp;

                            for(int k = -kw; k < kw; k++) {
                                tmp = this.pixels[MathUtils.clamp(x + k, 0, WIDTH-1)][y];
                                c.r += tmp.r * kernel[k+kw];
                                c.g += tmp.g * kernel[k+kw];
                                c.b += tmp.b * kernel[k+kw];
                            }

                            horiPixels[x][y] = c;
                        }
                    }
                }
            });
            horizontalThreads[i].start();
        }

        for(int i = 0; i < NUM_THREADS; i++) {
            try {
                horizontalThreads[i].join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //vertical blur
        Thread[] verticalThreads = new Thread[NUM_THREADS];
        for(int i = 0; i < NUM_THREADS; i++) {
            verticalThreads[i] = new Thread(new VerticalRunnable(horiPixels, pixmap, offsets[i], offsets[i+1]) {
                @Override
                public void run() {
                    for(int x = this.xstart; x < this.xend; x++) {
                        for(int y = 0; y < HEIGHT; y++) {
                            Color c = new Color();
                            Color tmp;

                            for(int k = -kw; k < kw; k++) {
                                tmp = horiPixels[x][MathUtils.clamp(y + k, 0, HEIGHT-1)];
                                c.r += tmp.r * kernel[k+kw];
                                c.g += tmp.g * kernel[k+kw];
                                c.b += tmp.b * kernel[k+kw];
                            }

                            c.a = 1.0f;
                            pixmap.drawPixel(x, y, Color.rgba8888(c));
                        }
                    }
                }
            });
            verticalThreads[i].start();
        }

        for(int i = 0; i < NUM_THREADS; i++) {
            try {
                verticalThreads[i].join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Texture(pixmap);
    }
    
    //old function but super slow
    /*private static Color blurredColor(int x, int y, Color[][] pixels) {
        float[][] kernel = {
                {0.005084f, 0.009377f, 0.013539f, 0.015302f, 0.013539f, 0.009377f, 0.005084f},
                {0.009377f, 0.017296f, 0.024972f, 0.028224f, 0.024972f, 0.017296f, 0.009377f},
                {0.013539f, 0.024972f, 0.036054f, 0.040749f, 0.036054f, 0.024972f, 0.013539f},
                {0.015302f, 0.028224f, 0.040749f, 0.046056f, 0.040749f, 0.028224f, 0.015302f},
                {0.013539f, 0.024972f, 0.036054f, 0.040749f, 0.036054f, 0.024972f, 0.013539f},
                {0.009377f, 0.017296f, 0.024972f, 0.028224f, 0.024972f, 0.017296f, 0.009377f},
                {0.005084f, 0.009377f, 0.013539f, 0.015302f, 0.013539f, 0.009377f, 0.005084f}};


        float r = 0, g = 0, b = 0;

        for(int kx = 0; kx < 7; kx++) {
            for(int ky = 0; ky < 7; ky++) {
                r += pixels[x + kx-3][y + ky-3].r * kernel[kx][ky];
                g += pixels[x + kx-3][y + ky-3].g * kernel[kx][ky];
                b += pixels[x + kx-3][y + ky-3].b * kernel[kx][ky];
            }
        }

        return new Color(r, g, b, 1);
    }*/
}
