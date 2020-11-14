<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	<SaleEnvelope>
		<xsl:for-each select="/SaleEnvelope/SaleList">
			<SaleList>
			<xsl:for-each select="Invoice">
				<xsl:if test="not(contains(Surname,'Shop'))">
				<!-- for each Invoice in SaleEnvelope/SaleList add this -->
					<Statement>
						<xsl:attribute name="Type">Monthly</xsl:attribute>
						<xsl:attribute name="Style">Full</xsl:attribute>
						<Customer>
							<Initials>
								<xsl:for-each select="Initial">
									<xsl:value-of select="."/>
								</xsl:for-each>
							</Initials>
							<!-- set the name of the person, found in the previous message in tag Surname -->
							<Name><xsl:value-of select="Surname"/></Name>
							<Balance><xsl:value-of select="Balance"/></Balance>
						</Customer>
						<Purchases>
							<xsl:for-each select="Item">
							
								<Article>
									<Desc><xsl:value-of select="Description"/></Desc>
									<Cost><xsl:value-of select='format-number((number(Price)*1.6),"####.##")'/></Cost>
									<Qty><xsl:value-of select="Quantity"/></Qty>
								</Article>
							
							</xsl:for-each>
						</Purchases>
						<Amount>
							<xsl:attribute name="Currency">
								<xsl:value-of select="Currency" />
							</xsl:attribute>
							<xsl:call-template name="sumSales">
								<xsl:with-param name="list" select="Item"/>
							</xsl:call-template>	
						</Amount>
					</Statement>								
				</xsl:if>
			</xsl:for-each>
			</SaleList>
		</xsl:for-each>
	</SaleEnvelope>
	</xsl:template>
	
	<xsl:template name="sumSales">
		<xsl:param name="list" />
		<xsl:param name="result"  select="0"/>

		<!-- Calculate the final price -->
		<xsl:choose>
			<xsl:when test="$list">
				<xsl:call-template name="sumSales">
					<xsl:with-param name="list"
						select="$list[position()!=1]"/>
					<xsl:with-param name="result" 
						select="$result + number($list[1]/Price)*number($list[1]/Quantity)*1.6"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select='format-number(number($result),"####.##")'/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
  
</xsl:stylesheet>