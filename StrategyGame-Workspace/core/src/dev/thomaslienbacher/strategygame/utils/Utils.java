package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.thomaslienbacher.strategygame.assets.Font;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Holds a couple of utilities.
 *
 * @author Thomas Lienbacher
 */
public class Utils {

    public static final float DEG_2_RAD = (float)(Math.PI / 180);
    public static final float RAD_2_DEG = (float)(1 / Math.PI * 180);

    //calculates the WIDTH of the string in the given font
    public static float calculateStringWidth(Font font, String string){
        GlyphLayout g = new GlyphLayout();
        g.setText(font.getBitmapFont(), string);
        return g.width;
    }

    //calculates the HEIGHT of the string in the given font
    public static float calculateStringHeight(Font font, String string){
        GlyphLayout g = new GlyphLayout();
        g.setText(font.getBitmapFont(), string);
        return g.height * font.getScale();//TODO debug: file bug report at libgdx repo
    }

    //replace string in every string in an array
    public static String[] replaceStringArray(String array[], String target, String replacement){
    	String replacedArray[] = array;

    	for(int i = 0; i < array.length; i++){
    		replacedArray[i] = replacedArray[i].replaceAll(target, replacement);
    	}

    	return replacedArray;
    }

    /**
     * Method used to get the path of a class file if its a standalone class file.
     *
     * @param c the class to get the path from
     * @return the path of the folder where the class file lies
     */
    public static String getPathOfClass(Class c){
        String path = "";
        path += System.getProperty("java.class.path") + "\\";
        path += c.getName().replace(c.getSimpleName(), "");
        path = path.substring(0, path.length()-1);
        path = path.replace(".", "\\");

        return path;
    }

    /**
     * Method used to get the path of the compiled jar file.
     *
     * Example: C:\Folder\Subfolder\runnable.jar
     *
     * @param c any class which is in the jar file
     * @return the path of the jar file
     */
    public static String getPathOfJar(Class c){
        String encodedPath = c.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path = "";

        try {
            path = URLDecoder.decode(encodedPath, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        path = path.replaceFirst("/", "");
        path = path.replace("/", "\\");

        return path;
    }

    //donwloads a file from the internet and saves it in destination
    public void downloadFile(String urlString, File destination) {
        try {
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Pixmap getPixmapFromFramebuffer(FrameBuffer frameBuffer) {
        Texture texture = frameBuffer.getColorBufferTexture();
        frameBuffer.bind();

        byte[] pixelData = ScreenUtils.getFrameBufferPixels(0, 0, texture.getWidth(), texture.getHeight(), false);
        Pixmap pixmap = new Pixmap(texture.getWidth(), texture.getHeight(), frameBuffer.getColorBufferTexture().getTextureData().getFormat());
        ByteBuffer pixels = pixmap.getPixels();
        pixels.clear();
        pixels.put(pixelData);
        pixels.position(0);

        FrameBuffer.unbind();

        return pixmap;
    }

    public static void setLinearFilter(Texture texture){
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void setNearestFilter(Texture texture){
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

}
