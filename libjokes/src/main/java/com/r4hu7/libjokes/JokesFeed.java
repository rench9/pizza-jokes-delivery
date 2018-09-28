package com.r4hu7.libjokes;

public class JokesFeed {

    private static final String[] jokes = {"If women ruled the world there would be no wars. Just a bunch of jealous countries not talking to each other. ",
            "Relationships are like fat people... Most of them don't work out.",
            "Old people at weddings always poke me and say you're next. So I started doing the same thing to them at funerals. ",
            "Light travels faster than sound. This is why some people appear bright until you hear them speak. ",
            "Light travels faster than sound. This is why some people appear bright until you hear them speak. ",
            "I always try to cheer myself up by singing when I get sad. Most of the time, it turns out that my voice is worse than my problems. ",
            "People say love is the best feeling, but I think finding a toilet when you've got diarrhea is better.",
            "People say love is the best feeling, but I think finding a toilet when you've got diarrhea is better.",
            "Don't kid yourself would be a great slogan for a condom company. ",
            "Dear rappers, please stop putting sirens in your songs. When I'm driving, it scares the crap out of me. ",
            "If your boyfriend remembers your eye color after the first date, then you probably have small boobs. ",
            "Don't be racist, be like Super Mario. He's an Italian plumber, created by Japanese people, who speaks English, and looks like a Mexican. ",
            "Politicians and diapers have one thing in common. They should both be changed regularly, and for the same reason."};

    public static String feedJoke() {
        return jokes[(int) (Math.random() * jokes.length)];
    }
}
