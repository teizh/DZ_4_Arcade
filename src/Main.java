import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 280, 320, 240, 90};
    public static int[] heroesDamage = {10, 15, 20, 5, 5, 10, 0};
    public static String[] heroesNames = {"WARRIOR", "WIZARD", "XAVIER", "BERSERK", "GOLEM", "LUCKY", "MEDIC"};
    public static String[] heroesAttackType = {"Physical", "Magical", "Telekinesis", "Physical", "Physical", "Magical", "Healing"};
    public static int roundNumber;
    public static int healing = 25;
    public static int coolDown = 0;


 /*   static Random random = new Random();
    public static int randomHero = random.nextInt(heroesHealth.length - 1);*/

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
        /*
        System.out.println(heroesNames[heroesNames.length - 1]); //Medic
        System.out.println(heroesNames[heroesNames.length - 2]); //Lucky
        System.out.println(heroesNames[heroesNames.length - 3]); //Golem
        System.out.println(heroesNames[heroesNames.length - 4]); //Berserk
        */
        if (coolDown == 3) {
            coolDown = 0;
        }

    }

    public static void playRound() {
        golemShields();
        healerHeal();
        luckyGotLucky();
        hitRedirect();
        roundNumber++;
        chooseBossDefence();

        bossHits();
        heroesHit();
        printStatistics();
        coolDown++;

    }


    public static void healerHeal() {

        Random random = new Random();
        int randomHero = random.nextInt(heroesHealth.length - 1);

        if (heroesHealth[heroesNames.length - 1] > 0 && heroesHealth[randomHero] > 0) {
            heroesHealth[randomHero] = heroesHealth[randomHero] + healing;
        }
    }

    public static void golemShields() {
        int shield = bossDamage/5;
        Random random = new Random();
        int randomHero2 = random.nextInt(heroesHealth.length - 1);
        if (heroesHealth[heroesNames.length - 2] > 0 && heroesHealth[randomHero2] > 0) {
            heroesHealth[randomHero2] = heroesHealth[randomHero2] + shield /*bossDamage / 5*/;
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 1); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }


    public static void hitRedirect() {
/*
        Random random = new Random();
        int randomIndex = random.nextInt(3); // 0, 1, 2
*/
        int redirected= bossDamage/2;


        if (coolDown == 3 && roundNumber != 1 && heroesHealth[heroesNames.length - 4] > 0) {
            heroesHealth[heroesNames.length - 4] = heroesHealth[heroesNames.length - 4] + redirected /*(1 / 2 * bossDamage)*/; //berserk
            bossHealth = bossHealth - redirected;
            System.out.println("Berserk strikes");
        }

    }

    public static void luckyGotLucky() {
/*        Random random = new Random();
        int randomIndex = random.nextInt(3); // 0, 1, 2*/
        int luck=bossDamage;

        if (coolDown == 2 && roundNumber != 1 && heroesHealth[heroesNames.length - 2] > 0) {
            heroesHealth[heroesNames.length - 2] = heroesHealth[heroesNames.length - 2] + luck /*bossDamage*/;
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = coefficient * heroesDamage[i];
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence = "No defence";
        if (bossDefence != null) {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " --------------");

        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " + (bossDefence != null ? bossDefence : "No defence"));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesNames[i] + " : " + heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
