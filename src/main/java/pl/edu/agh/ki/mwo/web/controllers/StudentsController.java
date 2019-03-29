package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentsController {

    @RequestMapping(value="/Students")
    public String listStudent(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	
        return "studentsList";    
    }
    
    @RequestMapping(value="/AddStudent")
    public String displayAddStudentForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
       	
        return "studentForm";    
    }

    @RequestMapping(value="/CreateStudent", method=RequestMethod.POST)
    public String createStudent(@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurame", required=false) String surname,
    		@RequestParam(value="studentPesel", required=false) String pesel,
    		@RequestParam(value="studentSchoolClass", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = new Student();
    	student.setName(name);
    	student.setSurname(surname);
    	student.setPesel(pesel);
    	
    	DatabaseConnector.getInstance().addStudent(student, schoolClassId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Nowa student został/a dodany/a");
         	
    	return "studentsList";
    }
    
    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST) //I am here now
    public String deleteStudent(@RequestParam(value="studentId", required=false) String StudentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteStudent(StudentId);    	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Student został(a) usunięty/a");
         	
    	return "studentsList";
    }

    @RequestMapping(value="/EditStudent", method=RequestMethod.POST)
    public String displayEditSchoolForm(@RequestParam (value="studentId", required=false) String studentId,
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	
    	model.addAttribute("student", DatabaseConnector.getInstance().getStudent(studentId));
        return "studentForm2";    
    }

    @RequestMapping(value="/UpdateStudent", method=RequestMethod.POST)
    public String editStudent(
    		@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurname", required=false) String surname, 
    		@RequestParam(value="studentPesel", required=false) String pesel, 
    		@RequestParam(value="studentId", required=false) String schoolId,
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().editStudent(name, surname, pesel, schoolId);    	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Dane studenta zostały zaktualizowane");
         	
    	return "studentsList";
    }
    

}