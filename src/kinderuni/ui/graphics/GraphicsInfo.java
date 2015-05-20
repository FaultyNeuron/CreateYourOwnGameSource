package kinderuni.ui.graphics;

import com.google.gson.Gson;

import java.io.*;

/**
 * Created by Georg Plaz.
 */
public class GraphicsInfo {
    private static final String GRAPHICS_INFO_FILE_NAME = "graphicsInfo.json";
    private String file_type;
    private String loop_type;
    private int fps;


    public String getFileType() {
        return file_type;
    }

    public static GraphicsInfo createInfo(File folder){
        try {
            return new Gson().fromJson(new InputStreamReader(new FileInputStream(new File(folder, GRAPHICS_INFO_FILE_NAME)), "UTF-8"), GraphicsInfo.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getFps() {
        return fps;
    }

    public AnimationLogicImpl.LoopType getLoopType(){
        return AnimationLogic.LoopType.valueOf(loop_type.toUpperCase());
    }
}
