package god.codename.brightside;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by Loknath Shankar on 5/9/2016.
 */
public class CorePattern {
    public ArrayList<BasicModel> GetAdapterToSet(ArrayList <BasicModel>CurrentAdapter,ArrayList <BasicModel> ReflectionFile,String TabName){
        ArrayList <BasicModel> AdapterToSet=new ArrayList<>();
        boolean doThis=true;
        if(TabName.equals(Main.TabNames.get(0))){
            doThis=false;
            for(int i=0;i<ReflectionFile.size();i++){
                AdapterToSet.add(ReflectionFile.get(i));
            }
        }
        for(int i=0;i<ReflectionFile.size() && doThis;i++){
            if(TabName.contains(ReflectionFile.get(i).getCategory())){
                AdapterToSet.add(ReflectionFile.get(i));
            }
        }
        return (AdapterToSet);
    }
    public ArrayList<BasicModel>FilterDuplicates(ArrayList <BasicModel> CurrentList,ArrayList <BasicModel> UpdatedList){
        ArrayList <BasicModel> FilteredList=new ArrayList<>();
        LinkedHashSet<BasicModel> hs = new LinkedHashSet<>();
        hs.addAll(FilteredList);
        FilteredList.clear();
        FilteredList.addAll(hs);
        return (FilteredList);
    }

    public String GetNextCallLink(ArrayList <BasicModel> ReflectionFile){
        return(ReflectionFile.get(ReflectionFile.size()-1).getNextCall());
    }

    public ArrayList<BasicModel> GetFileContents(String FileContents,Context context){
        DBHelper db= new DBHelper(context);
        try {
            CSVReader reader = new CSVReader(new StringReader(FileContents));
            String[] csvData;
            while ((csvData = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                if(csvData[HouseKeeping.NEWSID].length()>=1) {
                    HouseKeeping.ReflectionFile.add(new BasicModel(csvData[HouseKeeping.TITLE], csvData[HouseKeeping.SUMMARY], csvData[HouseKeeping.CATEGORY], csvData[HouseKeeping.SOURCE],
                            csvData[HouseKeeping.DATE], csvData[HouseKeeping.NEWSIMAGE], csvData[HouseKeeping.FULLTEXT], csvData[HouseKeeping.NEWSID], csvData[HouseKeeping.NEXTCALL],
                            csvData[HouseKeeping.NEWSLINK], csvData[HouseKeeping.AUTHOR], "false"));
                }
            }
            db.addNewsList(HouseKeeping.ReflectionFile);
        }catch (IOException e){/****/}
        HouseKeeping.ReflectionFile=db.getAllNews();
        return (HouseKeeping.ReflectionFile);
    }

    public boolean UpdateAllAdaptersArrays(ArrayList <BasicModel>ReflectionFile){
        CorePattern GetAdapter=new CorePattern();
        Main.TheTitleAdapterArray = GetAdapter.GetAdapterToSet(Main.TheTitleAdapterArray, ReflectionFile, Main.TabNames.get(0));
        Main.ComutingAdapterArray = GetAdapter.GetAdapterToSet(Main.ComutingAdapterArray,ReflectionFile, Main.TabNames.get(1));
        Main.InternetAdapterArray = GetAdapter.GetAdapterToSet(Main.InternetAdapterArray,ReflectionFile, Main.TabNames.get(2));
        Main.MobileAndGearAdapterArray = GetAdapter.GetAdapterToSet(Main.MobileAndGearAdapterArray,ReflectionFile, Main.TabNames.get(3));
        Main.BusinessAdapterArray = GetAdapter.GetAdapterToSet(Main.BusinessAdapterArray,ReflectionFile, Main.TabNames.get(4));
        Main.SecurityAdapterArray = GetAdapter.GetAdapterToSet(Main.SecurityAdapterArray,ReflectionFile, Main.TabNames.get(5));
        Main.RoboticsAdapterArray = GetAdapter.GetAdapterToSet(Main.RoboticsAdapterArray,ReflectionFile, Main.TabNames.get(6));
        Main.CultureAndDesignAdapterArray = GetAdapter.GetAdapterToSet(Main.CultureAndDesignAdapterArray,ReflectionFile, Main.TabNames.get(7));
        Main.ScienceAndBioAdapterArray = GetAdapter.GetAdapterToSet(Main.ScienceAndBioAdapterArray,ReflectionFile, Main.TabNames.get(8));
        Main.EnergyAndTransportAdapterArray = GetAdapter.GetAdapterToSet(Main.EnergyAndTransportAdapterArray,ReflectionFile, Main.TabNames.get(9));
        Main.FavAdapterArray = GetAdapter.GetFavList(ReflectionFile);
        Main.RedrawAdapter=true;
        return true;
    }

    public ArrayList<BasicModel> GetFavList(ArrayList <BasicModel> ReflectionFile){
        ArrayList <BasicModel> FavList=new ArrayList<>();
        for(int i=0;i< ReflectionFile.size();i++){
            if(ReflectionFile.get(i).getFav().compareTo("true")==0)
                FavList.add(ReflectionFile.get(i));
        }
        return (FavList);
    }
}
