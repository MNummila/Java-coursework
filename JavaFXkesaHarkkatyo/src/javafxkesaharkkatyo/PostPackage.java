/*
 * Tämä luokka sisältää paketin josta on periytetty pakettiluokat.
 */
package javafxkesaharkkatyo;

/**
 *
 * @author n4121
 */
public abstract class PostPackage {

    protected int packclass;
    protected int maxsize;
    protected int maxweight;
    protected String ekstra;
    protected String maxrangekm;

    public PostPackage(int c, int s, int w, String e, String r) {
        packclass = c;
        maxsize = s;
        maxweight = w;
        ekstra = e;
        maxrangekm = r;
    }

    public void getname() {
        System.out.println(this.getClass().getSimpleName());
    }
}

class firstclass extends PostPackage {

    public firstclass(int packclass, int maxsize, int maxweight, String ekstra, String maxrangekm) {
        super(packclass, maxsize, maxweight, ekstra, maxrangekm);
    }
}

class secondclass extends PostPackage {

    public secondclass(int packclass, int maxsize, int maxweight, String ekstra, String maxrangekm) {
        super(packclass, maxsize, maxweight, ekstra, maxrangekm);
    }

}

class thirdclass extends PostPackage {

    public thirdclass(int packclass, int maxsize, int maxweight, String ekstra, String maxrangekm) {
        super(packclass, maxsize, maxweight, ekstra, maxrangekm);
    }
}
