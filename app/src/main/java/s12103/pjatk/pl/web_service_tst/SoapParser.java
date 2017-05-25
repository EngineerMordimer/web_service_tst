package s12103.pjatk.pl.web_service_tst;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by maciej.chmielinski on 13.05.2017.
 */

public class SoapParser {
    public Vector<Lesson> readScheduleSoapResponse(SoapObject rootSoap) throws ParseException {
        Vector<Lesson> lessonVector = new Vector<>();
        String tmpBuilding, tmpCode, tmpName, tmpClassroom, tmpType;
        Date tmpBeginDate = new Date();
        Date tmpEndDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long tmpId;

        for (int i = 0; i < rootSoap.getPropertyCount(); i++) {
            Object property = rootSoap.getProperty(i);
            if (property instanceof SoapObject) {
                SoapObject category_list = (SoapObject) property;
                tmpBuilding = category_list.getProperty("Budynek").toString();
                try {
                    tmpBeginDate = dateFormat.parse(category_list.getProperty("Data_roz").toString());
                    tmpEndDate = dateFormat.parse(category_list.getProperty("Data_zak").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tmpCode = category_list.getProperty("Kod").toString();
                tmpName = category_list.getProperty("Nazwa").toString();
                tmpClassroom = category_list.getProperty("Nazwa_sali").toString();
                tmpType = category_list.getProperty("TypZajec").toString();
                tmpId = Long.parseLong(category_list.getProperty("idRealizacja_zajec").toString());

                lessonVector.add(new Lesson(tmpBuilding, tmpBeginDate, tmpEndDate, tmpCode, tmpName, tmpClassroom, tmpType, tmpId));
            }
        }
        return lessonVector;
    }

}
