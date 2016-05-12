package god.codename.brightside;

/**
 * Created by Loknath Shankar on 5/11/2016.
 */
public class NavDrawerModel {
    String Name;
    int ResId;
    int TabNumber;
    public NavDrawerModel(String Name,int ResId,int TabNumber){
        this.Name=Name;
        this.ResId=ResId;
        this.TabNumber=TabNumber;
    }

    public String getName() {return Name;}
    public int getResId() {return ResId;}
    public int getTabNumber() {return TabNumber;}
}
