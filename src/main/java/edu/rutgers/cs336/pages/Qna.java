package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
import edu.rutgers.cs336.services.UserSvc.Role;
import edu.rutgers.cs336.services.Database;

@Controller
@RequestMapping(value = "/qna") //so we can connect to the jsp page
public class Qna {

    private static record QuestionForm(String qtitle, String qbody) {}
    private static record AnswerForm(Integer qid, String abody) {}
    private static record LookUpForm(String qkeyword, String akeyword) {}

    @Autowired
    private QuestionSvc question;

    @Autowired
    private AnswerSvc answer;

    @Autowired
    private UserSvc users;

    /*@Autowired
    private Database db;*/

    @GetMapping
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        List<Question> qlist = question.index(); //The list of questions
        List<Answer> alist = answer.index(); //The list of answers
        Map<Integer, User> people = users.index().stream()
            .collect(Collectors.toMap(u -> u.id(), u -> u));

        model.addAttribute("qlist", qlist);
        model.addAttribute("alist", alist);
        model.addAttribute("users", people);

        return "qna";

    }

    /*@PostMapping("/customerQNA")
    public String askQuestion(@ModelAttribute QuestionForm form, HttpSession session, Model model) {

        var user = (User)session.getAttribute("user");
        var q = new Question(null, user.id(), form.qtitle(), form.qbody());
        question.add(q);

        return index(session, model);
    }*/

    @PostMapping//for when the customer asks the
    public String answerQuestion(@ModelAttribute QuestionForm form1, @ModelAttribute AnswerForm form2, @ModelAttribute LookUpForm form3, HttpSession session, Model model) {

        var user = (User)session.getAttribute("user");

        if (user.role() == Role.REPRESENTATIVE){

            if (form2 != null && form2.qid() != null){
                var a = new Answer(null, user.id(), form2.abody(), form2.qid());
                answer.add(a);
            }


            if (form3 != null){

                List<Question> qresults = question.findByKeyword(form3.qkeyword());
                List<Answer> aresults = answer.findByKeyword(form3.akeyword());

                model.addAttribute("qresults", qresults);
                model.addAttribute("aresults", aresults);

                return index(session, model);

            }

            List<Question> qresults = new ArrayList<Question>();
            List<Answer> aresults = new ArrayList<Answer>();

            model.addAttribute("qresults", qresults);
            model.addAttribute("aresults", aresults);

            return index(session, model);
        }

        //It is a customer

        if (form1 != null){
            var q = new Question(null, user.id(), form1.qtitle(), form1.qbody());
            question.add(q);
        }


        if (form3 != null){

            List<Question> qresults = question.findByKeyword(form3.qkeyword());
            List<Answer> aresults = answer.findByKeyword(form3.akeyword());

            model.addAttribute("qresults", qresults);
            model.addAttribute("aresults", aresults);

            return index(session, model);

        }

        List<Question> qresults = new ArrayList<Question>();
        List<Answer> aresults = new ArrayList<Answer>();

        model.addAttribute("qresults", qresults);
        model.addAttribute("aresults", aresults);

        return index(session, model);

    }

    /*@PostMapping("/lookup") //for when the customer asks the
    public String lookup(@ModelAttribute User form, HttpSession session, Model model) {



    }*/



}

//Need method to match the question to answer, do the id move

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
