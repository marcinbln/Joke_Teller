package com.example.javajoker;

public class JavaJoker {

    private String[] jokes = {
            "I just got fired from my job at the keyboard factory.\n \n They told me I wasn't putting in enough shifts.",
            "The oldest computer can be traced back to Adam and Eve. \n It was an apple but with extremely limited memory. Just 1 byte. \n And then everything crashed.",
            "Q: Why did Wi-Fi and the computer get married?\n \n" + "A: Because they had a connection.",
            "Q: How did the computer get out of the house?\n \n" + "A: He used windows.",
            "Q: What does a shark and a computer have in common?\n \n" + "A: They both have megabites.",
            "Q: What is the biggest lie in the entire universe?\n \n" + "A: \"I have read and agree to the Terms & Conditions.\"",
            "Bill Gates teaches a kindergarten class to count to ten.  \"1, 2, 3, 3.1, 95, 98, ME, 2000, XP, Vista, 7, 8, 8.1, 10.\" ",
            "Q: Why are elephants scared of computers? \n \n A: Because of the mouse.",
            "Q: How many programmers does it take to change a light bulb?\n \n" + "A: None. It's a hardware problem.",
            "Q: Why did the computer go to the doctor? \n \n A: Because it had a virus!",
    };

    public String getJavaJoke(int number) {

        return jokes[number];
    }

}
