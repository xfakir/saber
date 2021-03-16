package cn.xfakir.saber.test.web;

import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.annotation.Inject;
import cn.xfakir.saber.core.annotation.RequestMapping;
import cn.xfakir.saber.core.annotation.Value;
import cn.xfakir.saber.core.mvc.PathVariable;
import cn.xfakir.saber.core.mvc.RequestBody;
import cn.xfakir.saber.core.mvc.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${time}")
    private String time;

    @Inject
    private TestService testService;

    @RequestMapping("/test1")
    public String test() {
        return "test";
    }

    @RequestMapping("/test2/{girl}/{boy}")
    public String test2(@PathVariable String girl,@PathVariable String boy) {
        return girl + " and " + boy + " "+ testService.love() + " " + time;
    }

    @RequestMapping("/test3")
    public String test3(@RequestParam String girl,@RequestParam String boy) {
        return girl + " and " + boy;
    }

    @RequestMapping("/test4")
    public Lover test4(@RequestBody Lover lover) {
        return lover;
    }
}
