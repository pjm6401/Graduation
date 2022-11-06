package com.example.Graduation.Service;

import com.example.Graduation.DTO.RegisterForm;
import com.example.Graduation.Entity.RegisterData;
import com.example.Graduation.Repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
@Service
public class Register_Login_outService {
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결한다 즉 new 이렇게 객체를 선언 안해줘도 생성됨
    private RegisterRepository registerRepository;
    @Autowired
    PasswordEncoder passwordEncoder; // 암호화
    //아이디 검사
    public boolean checkIDDuplicate(String ID){
        return registerRepository.existsByID(ID);
    }
    // 이메일 검사
    public boolean checkemailDuplicate(String email){
        return registerRepository.existsByemail(email);
    }
    String message ;
    String url;
    public String getMessage(){
        return message;
    }
    public void setMessage(String m){
        this.message = m;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String u){
        this.url = u;
    }
    public void register(RegisterForm registerForm, Model model){
        RegisterData Rdata = registerForm.toEntity();
        System.out.println(Rdata.toString());

        if(checkIDDuplicate(Rdata.getID())){
            System.out.println("중복된 ID가 있습니다");
            /*model.addAttribute("message","중복된 ID 가 있습니다");
            model.addAttribute("returnurl","Home");
            return "/util/message";*/
            setMessage("중복된 ID가 있습니다.");
            setUrl("Home");
        }
        else if(checkemailDuplicate(Rdata.getEmail())) {
            System.out.println("중복된 Email이 있습니다");
            /*model.addAttribute("message","중복된 ID 가 있습니다");
            model.addAttribute("returnurl","Home");
            return "/util/message";*/
            setMessage("중복된 E-mail 이 있습니다.");
            setUrl("Home");
        }
        else {
            String pass = passwordEncoder.encode(Rdata.getPW());
            Rdata.setPassword(pass);
            //2. Repository에게 Entity를 DB에게 저장하게 한다
            RegisterData Rsaved = registerRepository.save(Rdata);
            System.out.println("회원가입 성공");
            /*model.addAttribute("message","회원가입 성공 환영합니다 !" + Rdata.getID() +"님");
            model.addAttribute("returnurl","Main");
            return "/util/message";*/
            setMessage("회원가입 성공 환영합니다 !" + Rdata.getID() +"님");
            setUrl("Main");
        }
    }
}
