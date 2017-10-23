package net.gefco.controlclientes.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gefco.controlclientes.modelo.TerceroTipo;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class ExcelBuilderTercerosTipo extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    	CellStyle cellStyle = workbook.createCellStyle();
    	CreationHelper createHelper = workbook.getCreationHelper();
    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    	    	
        //Creación del nombre del fichero
        response.setHeader("Content-Disposition", "attachment; filename=\"TercerosTipo.xls\""); //Ojo, quiero xlsx
        
        @SuppressWarnings("unchecked")
        List<TerceroTipo> tercerosTipo = (List<TerceroTipo>) model.get("tercerosTipoExcel");

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
                
        //Creación de la hoja de Excel
        Sheet sheet = workbook.createSheet("TERCEROS_TIPO_"+sdf.format(new Date()));

        //Creación de la fila títulos
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("TIPO");
        

        // Creación de las celdas
        int rowCount = 1;
        
        for (TerceroTipo terceroTipo : tercerosTipo){
            Row courseRow = sheet.createRow(rowCount++);
                        
            courseRow.createCell(0).setCellValue(terceroTipo.getTeti_nombre());

        }
        
        //Ajustar ancho columnas, en base a la fila de títulos
        for(int colNum = 0; colNum<header.getLastCellNum();colNum++)   
        	workbook.getSheetAt(0).autoSizeColumn(colNum);
        
    }

}