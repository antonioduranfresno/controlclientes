package net.gefco.controlclientes.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.modelo.TerceroGrupo;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class ExcelBuilderTercerosGrupo extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    	CellStyle cellStyle = workbook.createCellStyle();
    	CreationHelper createHelper = workbook.getCreationHelper();
    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    	    	
        //Creación del nombre del fichero
        response.setHeader("Content-Disposition", "attachment; filename=\"TercerosGrupo.xls\""); //Ojo, quiero xlsx
        
        @SuppressWarnings("unchecked")
        List<TerceroGrupo> tercerosGrupo = (List<TerceroGrupo>) model.get("tercerosGrupoExcel");

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
                
        //Creación de la hoja de Excel
        Sheet sheet = workbook.createSheet("TERCEROS_GRUPO_"+sdf.format(new Date()));

        //Creación de la fila títulos
        Row header = sheet.createRow(0);
        header.createCell(1).setCellValue("GRUPO");
        

        // Creación de las celdas
        int rowCount = 1;
        
        for (TerceroGrupo terceroGrupo : tercerosGrupo){
            Row courseRow = sheet.createRow(rowCount++);
                        
            courseRow.createCell(0).setCellValue(terceroGrupo.getTegr_nombre());

        }
        
        //Ajustar ancho columnas, en base a la fila de títulos
        for(int colNum = 0; colNum<header.getLastCellNum();colNum++)   
        	workbook.getSheetAt(0).autoSizeColumn(colNum);
        
    }

}