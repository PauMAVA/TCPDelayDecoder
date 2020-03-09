package me.PauMAVA.TCPDecoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonReader {

    private File file;

    public JsonReader(File file) {
        this.file = file;
    }

    public HashMap<Integer, Double> getDelayFromPackets() {
        try {
            String data = new String(Files.readAllBytes(this.file.toPath()));
            String regexFrameTime = Pattern.quote("\"frame.time_delta\"") + "(.*?)" + Pattern.quote(",");
            String regexFrameNum = Pattern.quote("\"frame.number\"") + "(.*?)" + Pattern.quote(",");
            Pattern frameTimePattern = Pattern.compile(regexFrameTime);
            Pattern frameNumPattern = Pattern.compile(regexFrameNum);
            Matcher frameTimeMatcher = frameTimePattern.matcher(data);
            Matcher frameNumMatcher = frameNumPattern.matcher(data);
            HashMap<Integer, Double> map = new HashMap<>();
            map.put(0, -1D);
            while (frameNumMatcher.find() && frameTimeMatcher.find()) {
                Integer i = Integer.parseInt(frameNumMatcher.group(1).replace(" ", "").replace("\t", "").replace("\n", "").replace(":", "").replace("\"", "").replace(",", ""));
                Double d = Double.parseDouble(frameTimeMatcher.group(1).replace(" ", "").replace("\t", "").replace("\n", "").replace(":", "").replace("\"", "").replace(",", ""));
                map.put(i, d);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<Integer, Double>();
        }
    }

}
