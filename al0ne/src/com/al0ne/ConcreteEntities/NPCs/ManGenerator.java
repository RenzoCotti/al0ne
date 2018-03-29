package com.al0ne.ConcreteEntities.NPCs;

import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.NPC;
import com.al0ne.Engine.Utility.Utility;

import java.util.ArrayList;

public class ManGenerator extends Generator{
    private ArrayList<String> headFeatures;
    private ArrayList<String> eyesFeatures;
    private ArrayList<String> faceFeatures;
    private ArrayList<String> bodyFeatures;
    private ArrayList<String> clothes;
    private ArrayList<String> male;
    private ArrayList<String> female;

    public ManGenerator(TechLevel techLevel) {
        this.headFeatures = new ArrayList<>();
        this.eyesFeatures = new ArrayList<>();
        this.faceFeatures = new ArrayList<>();
        this.bodyFeatures = new ArrayList<>();
        this.clothes = new ArrayList<>();
        this.male = new ArrayList<>();
        this.female = new ArrayList<>();

        this.headFeatures.add("is bald");
        this.headFeatures.add("has long hair");
        this.headFeatures.add("has short hair");

        this.eyesFeatures.add("has a penetrating stare");
        this.eyesFeatures.add("has blue eyes");
        this.eyesFeatures.add("has brown eyes");
        this.eyesFeatures.add("has green eyes");

        if(techLevel == TechLevel.MEDIUM){
            this.eyesFeatures.add("is wearing glasses");
        }

        this.faceFeatures.add("has a large nose");
        this.faceFeatures.add("has a small nose");
        this.faceFeatures.add("has really thin eyebrows");

        this.male.add("has a moustache");
        this.male.add("has a beard");
        this.male.add("is handsome");

        this.female.add("is very curvaceous");
        this.female.add("has a beautiful smile");
        this.female.add("is very beautiful");

        this.bodyFeatures.add("has a very large belly");
        this.bodyFeatures.add("is quite skinny");
        this.bodyFeatures.add("is quite muscular");
        this.bodyFeatures.add("is well built");
        this.bodyFeatures.add("is fairly young");
        this.bodyFeatures.add("is middle aged");
        this.bodyFeatures.add("is very old");

        switch (techLevel){
            case BASE:
                this.clothes.add("a leather outfit");
                this.clothes.add("some furs");
                this.clothes.add("a long dress made out of fibre");
                break;
            case LOW:
                this.clothes.add("a rough apron");
                this.clothes.add("a brown tunic");
                this.clothes.add("a green outfit with some brown pants");
                this.clothes.add("a yellow tunic");
                this.clothes.add("a brown outfit with large pants");
                break;
            case MEDIUM:
                this.clothes.add("a T-shirt and jeans");
                this.clothes.add("a black hoodie");
                this.clothes.add("a gray business suit");
                this.clothes.add("brown pants and a light blue shirt");
                this.clothes.add("a red checkered shirt and jeans");
                break;
            case HIGH:
                this.clothes.add("a T-shirt and ragged jeans");
                this.clothes.add("a nanite dress");
                this.clothes.add("a black suit");
                this.clothes.add("a kevlar vest");
                break;
        }



    }

    public String generateHead(){
        int randomHead = Utility.randomNumber(3);

        String head = "";
        switch (randomHead){
            case 1:
                head = headFeatures.get(Utility.randomNumber(headFeatures.size()));
                break;
            case 2:
                head = eyesFeatures.get(Utility.randomNumber(eyesFeatures.size()));
                break;
            case 3:
                head = faceFeatures.get(Utility.randomNumber(faceFeatures.size()));
                break;
        }
        return head;
    }

    public NPC generate(String name, String intro){
        String head = generateHead();

        int randBody = Utility.randomNumber(bodyFeatures.size());
        int randClothes = Utility.randomNumber(clothes.size());

        String description = name+" "+head+", "+bodyFeatures.get(randBody)+" and wears "+clothes.get(randClothes);


        return new NPC(name, description, intro);
    }

    public NPC generate(String name, String intro, boolean F){

        String head = generateHead();
        String body;

        if (Utility.randomGreaterThan(50)){
            if (F){
                body = female.get(Utility.randomNumber(female.size()));
            } else {
                body = male.get(Utility.randomNumber(male.size()));
            }
        } else {
            body = bodyFeatures.get(Utility.randomNumber(bodyFeatures.size()));
        }


        int randClothes = Utility.randomNumber(clothes.size());

        String description = name+" "+head+", "+body+" and wears "+clothes.get(randClothes);


        return new NPC(name, description, intro);
    }

}
