package net.gefco.controlclientes.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gefco.controlclientes.modelo.Tercero;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelBuilderTerceros extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    	CellStyle cellStyle = workbook.createCellStyle();
    	CreationHelper createHelper = workbook.getCreationHelper();
    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    	    	
        //Creaci�n del nombre del fichero
        response.setHeader("Content-Disposition", "attachment; filename=\"Terceros.xlsx\""); 
        
        @SuppressWarnings("unchecked")
        List<Tercero> terceros = (List<Tercero>) model.get("tercerosExcel");

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
                
        //Creaci�n de la hoja de Excel
        Sheet sheet = workbook.createSheet("TERCEROS_"+sdf.format(new Date()));

        //Creaci�n de la fila t�tulos
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("C�DIGO");        
        header.createCell(1).setCellValue("RAZ�N SOCIAL");
        header.createCell(2).setCellValue("TIPO");
        header.createCell(3).setCellValue("GRUPO");
        header.createCell(4).setCellValue("MARKET LINE");

        // Creaci�n de las celdas
        int rowCount = 1;
        
        for (Tercero tercero : terceros){
            Row courseRow = sheet.createRow(rowCount++);
                        
            courseRow.createCell(0).setCellValue(tercero.getTerc_codigo());
            courseRow.createCell(1).setCellValue(tercero.getTerc_razonSocial());
            courseRow.createCell(2).setCellValue(tercero.getTerceroTipo().getTeti_nombre());
            courseRow.createCell(3).setCellValue(tercero.getTerceroGrupo().getTegr_nombre());
            courseRow.createCell(4).setCellValue(tercero.getTerceroMarketLine().getTeml_nombre());

        }
        
        //Ajustar ancho columnas, en base a la fila de t�tulos
        for(int colNum = 0; colNum<header.getLastCellNum();colNum++)   
        	workbook.getSheetAt(0).autoSizeColumn(colNum);
        
    }

}