package marytts.unitselection.io;

import java.io.File;

public class Timeline {

    public static Timeline createFrom(File wavDir, File pmDir) {
        Timeline timeline = new Timeline();
        // read and store data, not yet implemented
        return timeline;
    }

    public void saveTo(File timelineFile) {
        // not yet implemented
    }

    public static void main(String[] args) {
        File wavDir;
        File pmDir;
        File timelineFile;
        try {
            wavDir = new File(args[0]);
            pmDir = new File(args[1]);
            timelineFile = new File(args[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        Timeline.createFrom(wavDir, pmDir).saveTo(timelineFile);
    }
}
