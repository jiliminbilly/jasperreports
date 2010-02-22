/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine;

import net.sf.jasperreports.engine.type.ResetTypeEnum;


/**
 * An interface for implementing classes that deal with report variables. This interface defines constants for names of
 * built-in variables and for reset, increment and calculation types.
 * <p>
 * When declaring a report group, the engine will automatically create a count variable that will calculate
 * the number of records that make up the current group (number of records processed between group ruptures).
 * The name for this variable comes from the name of the group it corresponds to, suffixed with the
 * "_COUNT" sequence. It can be used like any other report variable, in any report expression (even in the
 * current group expression like you can see done in the "BreakGroup" of the <i>jasper</i> sample).
 *
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public interface JRVariable extends JRCloneable
{
	/**
	 * Built-in variable that contains the total number of records read from the datasource. After finishing iterating throught the
	 * datasource, it will contain the total number of records that were processed.
	 */
	public static final String REPORT_COUNT = "REPORT_COUNT";


	/**
	 * Built-in variable containing the number of records that were processed when generating the current page.
	 */
	public static final String PAGE_COUNT = "PAGE_COUNT";


	/**
	 * This variable contains the number of records that were processed when generating the current column.
	 */
	public static final String COLUMN_COUNT = "COLUMN_COUNT";


	/**
	 * Built-in variable containing the current page number. At the end of the report filling process, it will contain the total
	 * number of pages for the resulting document.
	 */
	public static final String PAGE_NUMBER = "PAGE_NUMBER";


	/**
	 * Built-in variable containing the current column number.
	 */
	public static final String COLUMN_NUMBER = "COLUMN_NUMBER";


	/**
	 * @deprecated Replaced by {@link ResetTypeEnum#REPORT}.
	 */
	public static final byte RESET_TYPE_REPORT = 1;


	/**
	 * @deprecated Replaced by {@link ResetTypeEnum#PAGE}.
	 */
	public static final byte RESET_TYPE_PAGE = 2;


	/**
	 * @deprecated Replaced by {@link ResetTypeEnum#COLUMN}.
	 */
	public static final byte RESET_TYPE_COLUMN = 3;


	/**
	 * @deprecated Replaced by {@link ResetTypeEnum#GROUP}.
	 */
	public static final byte RESET_TYPE_GROUP = 4;


	/**
	 * @deprecated Replaced by {@link ResetTypeEnum#NONE}.
	 */
	public static final byte RESET_TYPE_NONE = 5;


	/**
	 * The value is calculated by simply evaluating the variable expression.
	 */
	public static final byte CALCULATION_NOTHING = 0;


	/**
	 * The value is calculated by counting the non-null values of the variable expression with every iteration in the data source.
	 * The count variable must be numeric, but the variable expression needs not, since its value is not important.
	 * On the other hand, the initial value expression must be numeric since it will be the count variable initial value.
	 */
	public static final byte CALCULATION_COUNT = 1;


	/**
	 * The value is calculated by summing up the values returned by the variable's expression. Both the main expression and initial
	 * expression must have numeric type.
	 *
	 */
	public static final byte CALCULATION_SUM = 2;


	/**
	 * The value is obtained by calculating the average for the series of values obtained by evaluating the variable's
	 * expression for each record in the data source. Both the main expression and initial expression must have numeric type.
	 * <p>
	 * In order to calculate the average, the engine creates behind the scenes a helper report variable that calculates
	 * the sum of the values and uses it to calculate the average for those values. This helper sum variable gets its
	 * name from the corresponding average variable suffixed with "_SUM" sequence. This helper variable can be used
	 * in other report expressions just like any normal variable.
	 */
	public static final byte CALCULATION_AVERAGE = 3;


	/**
	 * The value of the variable represents the lowest in the series of values obtained by evaluating the variable's
	 * expression for each data source record.
	 */
	public static final byte CALCULATION_LOWEST = 4;


	/**
	 * The value of the variable represents the highest in the series of values obtained by evaluating the variable's
	 * expression for each data source record.
	 */
	public static final byte CALCULATION_HIGHEST = 5;


	/**
	 * The value is obtained by calculating the standard deviation for the series of values returned by evaluating the
	 * variable's expression.
	 * <p>
	 * Just like for the variables that calculate the average, the engine creates and uses helper report variables
	 * for first obtaining the sum and the count that correspond to your current series of values. The name for
	 * those helper variables that are created behind the scenes is obtained by suffixing the user variable with
	 * the "_SUM" or "_COUNT" suffix and they can be used in other report expressions like any other report variable.
	 * <p>
	 * For variables that calculate the standard deviation, there is always a helper variable present, that first
	 * calculates the variance for the series of values and it has the "_VARIANCE" suffix added to its name.
	 */
	public static final byte CALCULATION_STANDARD_DEVIATION = 6;


	/**
	 * The value is obtained by calculating the variance for the series of values returned by evaluating the
	 * variable's expression.
	 */
	public static final byte CALCULATION_VARIANCE = 7;


	/**
	 * The value is not calculated by JasperReports. The user must calculate the value of the variable, almost
	 * certainly using the scriptlets functionality. For this type of calculation, the only thing the engine does is
	 * to conserve the value users have calculated, from one iteration in the data source to the next.
	 */
	public static final byte CALCULATION_SYSTEM = 8;
	
	
	/**
	 * The variable keeps the first value and does not increment it on subsequent iterations.
	 */
	public static final byte CALCULATION_FIRST = 9;


	/**
	 * The value is calculated by counting the distinct non-null values of the variable expression with every iteration in the data source.
	 * The count variable must be numeric, but the variable expression needs not, since its value is not important.
	 * On the other hand, the initial value expression must be numeric since it will be the count variable initial value.
	 */
	public static final byte CALCULATION_DISTINCT_COUNT = 10;


	/**
	 * Returns the name of the variable. Since all variables are stored in a map, the variable names are the keys in the map.
	 * @return a string containing the variable name
	 */
	public String getName();


	/**
	 * Returns the class of the variable value. Any class is allowed as long as it is in the classpath at compile and run time.
	 * @return a <tt>Class</tt> instance representing the variable value class
	 */
	public Class getValueClass();
		
	/**
	 * Returns the string name of the variable value class.
	 */
	public String getValueClassName();
		
	/**
	 * Returns the class of the incrementer factory used for choosing the right incrementer for the variable value.
	 * @return the <tt>Class</tt> instance of the incrementer factory
	 * @see net.sf.jasperreports.engine.fill.JRIncrementer
	 * @see net.sf.jasperreports.engine.fill.JRIncrementerFactory
	 */
	public Class getIncrementerFactoryClass();
		
	/**
	 * Returns the string name of the variable value class.
	 */
	public String getIncrementerFactoryClassName();
		
	/**
	 * @deprecated Replaced by {@link getResetTypeValue()}.
	 */
	public byte getResetType();
		
	/**
	 * @deprecated Replaced by {@link getIncrementTypeValue()}.
	 */
	public byte getIncrementType();
		
	//TODO: uncomment these below
	
//	/**
//	 * Returns the variable reset type.
//	 * @return a value representing one of the reset type constants in {@link ResetTypeEnum}
//	 */
//	public ResetTypeEnum getResetTypeValue();
//	
//	/**
//	 * Returns the variable increment type.
//	 * @return a value representing one of the reset type constants in {@link ResetTypeEnum}
//	 */
//	public ResetTypeEnum getIncrementTypeValue();
	
	/**
	 * Returns the variable calculation type. This value must be one of the calculation constants declared in this class.
	 */
	public byte getCalculation();

	/**
	 * Returns <code>true</code> if the variable calculation type is system defined.
	 * @see JRVariable#CALCULATION_SYSTEM
	 */
	public boolean isSystemDefined();

	/**
	 * Returns the main expression for this variable. The expression must be numeric for certain calculation types.
	 * @return a {@link JRExpression} instance containing the expression.
	 */
	public JRExpression getExpression();
		
	/**
	 * Returns the initial value expression for this variable. The expression must be numeric for certain calculation types.
	 * @return a {@link JRExpression} instance containing the initial expression.
	 */
	public JRExpression getInitialValueExpression();
		
	/**
	 * Returns the group whose break triggers the variable reset. Only used when {@link JRVariable#getResetType()} returns
	 * {@link JRVariable#RESET_TYPE_GROUP}.
	 */
	public JRGroup getResetGroup();
		
	/**
	 * Returns the group whose break triggers the variable increment. Only used when {@link JRVariable#getIncrementType()} returns
	 * {@link JRVariable#RESET_TYPE_GROUP}.
	 */
	public JRGroup getIncrementGroup();
		
}
