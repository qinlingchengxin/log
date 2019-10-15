package net.ys.controller;

import net.ys.bean.PathInfo;
import net.ys.conf.SysConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: NMY
 * Date: 2019-10-15
 * Time: 14:40
 */
@Controller
public class LogController {

    @Resource
    private SysConfig sysConfig;

    @GetMapping("/")
    public String logs(Model model) throws UnsupportedEncodingException {
        List<String> path = sysConfig.getPath();
        List<PathInfo> files = new ArrayList<>();
        for (String p : path) {
            File[] list = new File(p).listFiles();
            for (File s : list) {
                String tmp = s.getAbsolutePath().replace('\\', '/');
                tmp = tmp.substring(tmp.indexOf(':') + 1);
                files.add(new PathInfo(tmp, URLEncoder.encode(tmp, "utf-8")));
            }
            files.add(new PathInfo("-----------------------------------------------------------------------", ""));
        }
        model.addAttribute("files", files);
        return "index";
    }

    @GetMapping("content")
    public void content(HttpServletResponse response, String path) throws IOException {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain");
        ServletOutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) > 0) {
            out.write(bytes, 0, len);
            out.flush();
        }
        out.close();
        inputStream.close();
    }
}
