package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*import main.java.edu.rutgers.cs336.services.QuestionSvc;
import main.java.edu.rutgers.cs336.services.QuestionSvc.Question;
import main.java.edu.rutgers.cs336.services.AnswerSvc;
import main.java.edu.rutgers.cs336.services.AnswerSvc.Answer;
import main.java.edu.rutgers.cs336.services.UserSvc;
import main.java.edu.rutgers.cs336.services.UserSvc.User;*/

import edu.rutgers.cs336.services.QuestionSvc;
import edu.rutgers.cs336.services.QuestionSvc.Question;
import edu.rutgers.cs336.services.AnswerSvc;
import edu.rutgers.cs336.services.AnswerSvc.Answer;
import edu.rutgers.cs336.services.UserSvc;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping(value = "/qna") //so we can connect to the jsp page
public class Qna {
    
    @Autowired
    private QuestionSvc question;
    
    @Autowired
    private AnswerSvc answer;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        List<Question> qlist = question.index();
        List<Answer> alist = answer.index();

        model.addAttribute("qlist", qlist);
        model.addAttribute("alist", alist);

        return "qna";
    }


}

/*@Controller
@RequestMapping("/qna")
public class Qna {
    @Autowired
    private UserSvc users;

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        List<User> list = users.getCustomersReps();
        model.addAttribute("list", list);
        return "qna";
    }

}*/
