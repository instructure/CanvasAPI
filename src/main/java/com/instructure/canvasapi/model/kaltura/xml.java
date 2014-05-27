package com.instructure.canvasapi.model.kaltura;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by nbutton on 5/22/14.
 */
@Root
public class xml {
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    /*
            <?xml version="1.0" encoding="UTF-8"?>
        <xml>
           <xml>
              <objectType>xml</objectType>
              <id>0_5e929ce09b1155753a3921e78d65e992</id>
              <partnerId>156652</partnerId>
              <userId>231890_10</userId>
              <status>2</status>
              <fileName>2010-07-19 13:20:06 -0600.mov</fileName>
              <fileSize />
              <uploadedFileSize>213603</uploadedFileSize>
              <createdAt>1279570943</createdAt>
              <updatedAt>1279571114</updatedAt>
           </xml>
           <executionTime>0.051121950149536</executionTime>
        </xml>
             */
    @Element
    private Result result;

    @Element(required = false)
    private float executionTime;


}

