package chatbot;

import chatbot.cortana.Cortana;

public class Main {
    
    public static void main(String[] args) {
        Cortana cortana = new Cortana(".");
        cortana.run();
    }

}
