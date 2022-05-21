package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;

public class Futil {
    public static void processDir(String dirName, String resultFileName){
        Charset sourceCharset=Charset.forName("Cp1250");
        Charset resultCharset=Charset.forName("UTF-8");
        Path sourcePath= Paths.get(dirName);
        Path resultPath=Paths.get(resultFileName);

        try {
            FileChannel result = FileChannel.open(resultPath, StandardOpenOption.APPEND,StandardOpenOption.CREATE);

            Files.walkFileTree(sourcePath,new SimpleFileVisitor<Path>(){
                public FileVisitResult visitFile(Path path) throws IOException {
                    FileChannel source=FileChannel.open(path);
                    ByteBuffer byteBuffer=ByteBuffer.allocateDirect((int) source.size());
                    while(source.read(byteBuffer)>0){
                        source.read(byteBuffer);
                        byteBuffer.flip();
                        CharBuffer charBuffer=sourceCharset.decode(byteBuffer);
                        result.write(resultCharset.encode(charBuffer));
                    }
                    source.close();
                    result.write(resultCharset.encode("\n"));

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
