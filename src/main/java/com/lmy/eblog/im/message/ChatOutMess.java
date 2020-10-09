package com.lmy.eblog.im.message;


import com.lmy.eblog.im.vo.ImMess;
import lombok.Data;

@Data
public class ChatOutMess {

    private String emit;
    private ImMess data;

}
