﻿// JScript 文件
function add(btn)
{
   var h = "";
   if(btn=="item")
   {
      h="ZBJL/Work.aspx";
   }
   if(btn=="YQ")
   {
      h ="ZBYQ/Work.aspx";
   }
   if(btn=="NB")
   {
      h="NBYJ/Work.aspx";
   }
   if(btn=="HB")
   {
      h="JSHB/Work.aspx";
   }
   if(btn=="DH")
   {
      h="NBDH/Work.aspx";
   }
   if(btn=="HC")
   {
      h="JSHC/Work.aspx";
   }
   if(btn=="XX")
   {
      h="ZBXX/Work.aspx";
   }
   window.parent.frames["mainFrame"].location.href=h;
}
function search(btn)
{
   var h = "";
   if(btn=="item")
   {
      h="ZBJL/List.aspx";
   }
   if(btn=="item2")
   {
      h="WorkPage.aspx";
   }
   if(btn=="item3")
   {
      h="ZBJL/Work.aspx";
   }
   if(btn=="YQ")
   {
      h ="ZBYQ/List.aspx";
   }
   if(btn=="NB")
   {
      h="NBYJ/List.aspx";
   }
   if(btn=="HB")
   {
      h="JSHB/List.aspx";
   }
   if(btn=="DH")
   {
      h="NBDH/List.aspx";
   }
   if(btn=="HC")
   {
      h="JSHC/List.aspx";
   }
   if(btn=="XX")
   {
      h="ZBXX/List.aspx";
   }
   if(btn=="QT")
   {
      //h="QT/List.aspx";
      h="QT/BlankTemp.aspx"
   }
   if(btn=="zbbrl")
   {
      h="../bpzb/bpzb_ClendarView.aspx";
   }
   if(btn=="QTDH")
   {
      h="DHJL/List.aspx";
   }
   window.parent.frames["mainFrame"].location.href=h;
}

