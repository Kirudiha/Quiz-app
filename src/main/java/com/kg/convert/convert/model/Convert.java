package com.kg.convert.convert.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
    public class Convert {
       @Id
       @GeneratedValue(strategy=GenerationType.AUTO)
       private Long id;
            
    @Column
    public String question;
    public String code;
    public String language;
    public String op1;
    public String op2;
    public String op3;
    public String op4;
    public String correctoption;
   


    /**
     * @param correctoption the correctoption to set
     */
    public void setCorrectoption(String correctoption) {
        this.correctoption = correctoption;
    }
    /**
     * @return the correctoption
     */
    public String getCorrectoption() {
        return correctoption;
    }

/**
 * @return the op3
 */
public String getOp3() {
    return op3;
}
/**
 * @return the op4
 */
public String getOp4() {
    return op4;
}

/**
 * @param op1 the op1 to set
 */
public void setOp1(String op1) {
    this.op1 = op1;
}
/**
 * @param op2 the op2 to set
 */
public void setOp2(String op2) {
    this.op2 = op2;
}
/**
 * @param op3 the op3 to set
 */
public void setOp3(String op3) {
    this.op3 = op3;
}
/**
 * @param op4 the op4 to set
 */
public void setOp4(String op4) {
    this.op4 = op4;
}


/**
 * @param id the id to set
 */
public void setId(Long id) {
    this.id = id;
}

/**
 * @return the language
 */
public String getLanguage() {
    return language;
}

/**
 * @param language the language to set
 */
public void setLanguage(String language) {
    this.language = language;
}

/**
 * @return the id
 */
public Long getId() {
    return id;
}
/**
 * @return the op1
 */
public String getOp1() {
    return op1;
}
/**
 * @return the op2
 */
public String getOp2() {
    return op2;
}

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

   
    @Override
    public String toString() {
        return "question"+question+"code "+code + "language "+ language+ " op1 " + op1 + " op2 "+op2+ " op3 "+op3 + " op4 "+op4;
    }  
    }
