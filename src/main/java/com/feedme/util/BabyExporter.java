package com.feedme.util;

import android.content.Context;
import android.util.Log;
import com.feedme.dao.DiaperDao;
import com.feedme.dao.JournalDao;
import com.feedme.dao.NapDao;
import com.feedme.model.Baby;
import com.feedme.model.Diaper;
import com.feedme.model.Journal;
import com.feedme.model.Nap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 11:18 AM
 *
 * Util class that creates Excel or CSV files with all data for a Baby.
 */
public class BabyExporter {

    /**
     * Creates an Excel spreadsheet with all Baby data for the passed in baby. This file is stored on the external
     * storage.
     *
     * @param context - Android Context
     * @param baby - Baby Pojo
     * @return
     */
    public static boolean exportBabyToXls(Context context, Baby baby) {

        boolean success = false;

        JournalDao journalDao = new JournalDao(context);
        NapDao napDao = new NapDao(context);
        DiaperDao diaperDao = new DiaperDao(context);

        // check if available and not read only
        if (!FeedMeUtil.isExternalStorageAvailable() || FeedMeUtil.isExternalStorageReadOnly()) {
            Log.w("BabyExporter", "Storage not available or read only");
            return success;
        }

        Workbook wb = new HSSFWorkbook();
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);

        //Feedings Sheet
        List<Journal> allFeedings = journalDao.getAllEntriesForChild(baby.getId());
        Sheet feedings = wb.createSheet("Feedings");

        //Feedings Headings
        Row row = feedings.createRow((short)0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(1);
        cell.setCellValue("Feeding Type");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(2);
        cell.setCellValue("Start Time");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(3);
        cell.setCellValue("End Time");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(4);
        cell.setCellValue("Feed Time");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(5);
        cell.setCellValue("Side");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(6);
        cell.setCellValue("Ounces");
        cell.setCellStyle(headerStyle);

        //Feedings Data
        for(int i=0 ; i<allFeedings.size() ; i++){
            Journal journal = allFeedings.get(i);

            row = feedings.createRow((short)i+1);

            //Date
            cell = row.createCell(0);
            cell.setCellValue(journal.getDate());

            //Feeding Type
            cell = row.createCell(1);
            if(journal.getSide()!=null && !journal.getSide().equals("")){
                cell.setCellValue("Breast Feeding");
            } else {
                cell.setCellValue("Bottle Feeding");
            }

            //Start Time
            cell = row.createCell(2);
            cell.setCellValue(journal.getStartTime());

            //End Time
            cell = row.createCell(3);
            cell.setCellValue(journal.getEndTime());

            //Feed Time
            cell = row.createCell(4);
            cell.setCellValue(journal.getFeedTime());

            //Side
            cell = row.createCell(5);
            cell.setCellValue(journal.getSide());

            //Ounces
            cell = row.createCell(6);
            cell.setCellValue(journal.getOunces());
        }
        
        //Naps Sheet
        List<Nap> allNaps = napDao.getAllNapsByChild(baby.getId());
        Sheet naps = wb.createSheet("Naps");

        //Naps Headings
        row = naps.createRow((short)0);
        
        cell = row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(1);
        cell.setCellValue("Start Time");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(2);
        cell.setCellValue("End Time");
        cell.setCellStyle(headerStyle);
        
        cell = row.createCell(3);
        cell.setCellValue("Location");
        cell.setCellStyle(headerStyle);

        //Feedings Data
        for(int i=0 ; i<allNaps.size() ; i++){
            Nap nap = allNaps.get(i);

            row = naps.createRow((short)i+1);

            //Date
            cell = row.createCell(0);
            cell.setCellValue(nap.getDate());

            //Start Time
            cell = row.createCell(1);
            cell.setCellValue(nap.getStartTime());

            //End Time
            cell = row.createCell(2);
            cell.setCellValue(nap.getEndTime());

            //Location
            cell = row.createCell(3);
            cell.setCellValue(nap.getLocation());
        }

        //Diapers Sheet
        List<Diaper> allDiapers = diaperDao.getAllDiapersByChild(baby.getId());
        Sheet diapers = wb.createSheet("Diapers");

        //Diapers Headings
        row = diapers.createRow((short)0);

        cell = row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(1);
        cell.setCellValue("Time");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(2);
        cell.setCellValue("Type");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(3);
        cell.setCellValue("Consistency");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(4);
        cell.setCellValue("Color");
        cell.setCellStyle(headerStyle);

        //Feedings Data
        for(int i=0 ; i<allDiapers.size() ; i++){
            Diaper diaper = allDiapers.get(i);

            row = diapers.createRow((short)i+1);

            //Date
            cell = row.createCell(0);
            cell.setCellValue(diaper.getDate());

            //Time
            cell = row.createCell(1);
            cell.setCellValue(diaper.getTime());

            //Type
            cell = row.createCell(2);
            cell.setCellValue(diaper.getType());

            //Consistency
            cell = row.createCell(3);
            cell.setCellValue(diaper.getConsistency());

            //Color
            cell = row.createCell(4);
            cell.setCellValue(diaper.getColor());
        }

        //Write Workbook to External Storage
        File file = new File(context.getExternalFilesDir(null), baby.getName() + ".xls");
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("BabyExporter", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("BabyExporter", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("BabyExporter", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
                Log.v("BabyExporter", "Failed to close OutputStream", ex);
            }
        }

        return success;
    }
}