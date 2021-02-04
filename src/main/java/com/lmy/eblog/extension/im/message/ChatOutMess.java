package com.lmy.eblog.extension.im.message;


import com.lmy.eblog.extension.im.vo.ImMess;
import lombok.Data;

@Data
public class ChatOutMess {

    private String emit;
    private ImMess data;

}
