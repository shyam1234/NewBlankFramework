package com.stpl.edurp.models;

import com.google.gson.annotations.SerializedName;
import com.stpl.edurp.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 22-02-2017.
 */

public class GetMobileDetailsDataModel extends IModel
{
 /*{
  "MessageBody": [
    {
      "MessageBodyHTML": "<p><b>Republic Day</b> honors the date on which the <a href=\"/wiki/Indian_constitution\" class=\"mw-redirect\" title=\"Indian constitution\">Constitution of India</a> came into effect on 26 January 1950 replacing the <a href=\"/wiki/Government_of_India_Act_1935\" class=\"mw-redirect\" title=\"Government of India Act 1935\">Government of India Act</a> (1935) as the governing document of India.<sup id=\"cite_ref-law_min_intro_1-0\" class=\"reference\"><a href=\"#cite_note-law_min_intro-1\">[1]</a></sup></p>\\\\\\\r\\\\\\\n<p>The Constitution was adopted by the Indian Constituent Assembly on 26 November 1949, and came into effect on 26 January 1950 with a democratic government system, completing the country's transition towards becoming an independent <a href=\"/wiki/Republic\" title=\"Republic\">republic</a>. 26 January was chosen as the Republic day because it was on this day in 1930 when Declaration of Indian Independence (Purna Swaraj) was proclaimed by the Indian National Congress as opposed to the Dominion status offered by British Regime.</p>\\\\\\\r\\\\\\\n<p>It is one of three <a href=\"/wiki/Public_holidays_in_India\" title=\"Public holidays in India\">national holidays</a> in <a href=\"/wiki/India\" title=\"India\">India</a>, the other two being <a href=\"/wiki/Independence_Day_(India)\" title=\"Independence Day (India)\">Independence Day</a> and <a href=\"/wiki/Gandhi_Jayanti\" title=\"Gandhi Jayanti\">Gandhi Jayanti</a>.</p>"
    }
  ],
  "Documents": [
    {
      "DocumentMasterId": 51,
      "DocumentId": 52,
      "ReferenceId": 2,
      "MenuCode": "NEW",
      "DocumentName": "Picture",
      "DocumentPath": "https://upload.wikimedia.org/wikipedia/commons/e/e2/Rashtrapati_Bhavan_and_adjacent_buildings%2C_illuminated_for_the_Republic_Day.jpg",
      "DocumentExtn": ".PNG",
      "IsAttachment": 0,
      "MediaType": "I",
      "SortOrder": 1,
      "FileURL": "https://edurpstorage.blob.core.windows.net/edurpcontainer/DEV/0/51/52?sv=2015-12-11&sr=c&sig=71YMTMHXmp%2B7zkI8z9KQx9sWcavfjRCOO%2Bh9liLTAFI%3D&se=2017-02-23T15%3A07%3A48Z&sp=rwl"
    },
    ]
}*/


    @SerializedName("MessageBody")
    private ArrayList<MessageBodyDataModel> MessageBody;

    @SerializedName("Documents")
    private ArrayList<TableDocumentMasterDataModel> Documents;

    public ArrayList<MessageBodyDataModel> getMessageBody() {
        return MessageBody;
    }

    public ArrayList<TableDocumentMasterDataModel> getDocuments() {
        return Documents;
    }


    public static class MessageBodyDataModel {
        @SerializedName("MessageBodyHTML")
        private String MessageBodyHTML;

        public String getMessageBodyHTML()
        {
            return MessageBodyHTML;
        }

        public void setMessageBodyHTML(String messageBodyHTML) {
            MessageBodyHTML = messageBodyHTML;
        }
    }
}
