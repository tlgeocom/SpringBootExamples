package com.demo.controller;

import com.demo.common.Result;
import com.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.opencv.core.CvType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.function.ToIntFunction;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;
/**
 * <p> @Title DemoController
 * <p> @Description 测试Controller
 *
 * @author ACGkaka
 * @date 2023/4/24 18:02
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public Result<Object> test() throws InterruptedException {
        Result<Object> result = Result.succeed();
        log.info(">>>>>>>>>>【INFO】DemoController.test()...");
        int msg = demoService.test();
        return result.setData(msg);
    }
    public static void testJAI() throws IOException {
        String path = "D:\\1.jpg";
        File inputFile = new File(path);
        Image result = ImageIO.read(inputFile);
        log.debug("result..getSource():"+result.getSource());
    }
}
