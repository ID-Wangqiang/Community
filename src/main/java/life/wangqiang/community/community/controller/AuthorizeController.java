package life.wangqiang.community.community.controller;

import life.wangqiang.community.community.dto.AccessTokenDTO;
import life.wangqiang.community.community.dto.GithubUser;
import life.wangqiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version : V1.0
 * @ClassName: AuthorizeController
 * @Description: TODO
 * @Auther: wangqiang
 * @Date: 2019/12/17 上午10:39
 */
@Controller
@Component
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        System.out.println(clientId);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
//        System.out.println(clientSecret);
        accessTokenDTO.setClient_secret(clientSecret);
//        System.out.println(redirectUri);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
