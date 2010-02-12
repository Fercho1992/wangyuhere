<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns2="http://www.example.org/ApplicantProfile" xmlns:ns3="http://www.example.org/ShortCV" xmlns:ns4="http://www.example.org/EmploymentRecord">
<xsl:template match="/">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Applicant Profile</title>
<style type="text/css">
<xsl:comment>
.style1 {
	font-size: x-large;
	font-weight: bold;
}
</xsl:comment>
</style>
</head>

<body>
<table width="592" height="237" border="0">
  <tr>
    <td colspan="4"><span class="style1">Personal Information</span></td>
  </tr>
  <tr>
    <td width="129">Name:</td>
    <td width="216"><xsl:value-of select="//@name"></xsl:value-of></td>
    <td width="91">Gender:</td>
    <td width="138"><xsl:value-of select="ns2:applicantProfile/ns3:personalInformation/ns3:gender"/></td>
  </tr>
  <tr>
    <td>Age:</td>
    <td><xsl:value-of select="ns2:applicantProfile/ns3:personalInformation/ns3:age"/></td>
    <td>Nationality:</td>
    <td><xsl:value-of select="ns2:applicantProfile/ns3:personalInformation/ns3:country"/></td>
  </tr>
  <tr>
    <td>Hobby:</td>
    <td colspan="3"><xsl:value-of select="ns2:applicantProfile/ns3:personalInformation/ns3:hobby"/></td>
  </tr>
  <tr>
    <td>Phone:</td>
    <td colspan="3"><xsl:value-of select="ns2:applicantProfile/ns3:contactInformation/ns3:phoneNo."/></td>
  </tr>
  <tr>
    <td>Email:</td>
    <td colspan="3"><xsl:value-of select="ns2:applicantProfile/ns3:contactInformation/ns3:email"/></td>
  </tr>
  <tr>
    <td height="30">Personal Statement:</td>
    <td colspan="3"><xsl:value-of select="ns2:applicantProfile/ns3:personalInformation/ns3:personalStatement"/></td>
  </tr>
</table>
<p></p>
<table width="590" height="76" border="0">
  <tr>
    <td><span class="style1">Education</span></td>
  </tr>
  <tr>
    <td><table width="580" height="82" border="0">
      <tr>
        <td width="117">University:</td>
        <td width="447"><xsl:value-of select="ns2:applicantProfile/ns2:education/ns2:educationItem/ns2:university"/></td>
      </tr>
      <tr>
        <td>Degree:</td>
        <td><xsl:value-of select="ns2:applicantProfile/ns2:education/ns2:educationItem/ns2:degree"/></td>
      </tr>
      <tr>
        <td colspan="2">Courses:</td>
      </tr>
      <tr>
        <td colspan="2">
        
          <table width="575" height="81" border="1">
            <tr>
              <td width="375">Course Name</td>
              <td width="95">Grade</td>
              <td width="91">Ect</td>
              </tr>
              <xsl:for-each select="ns2:applicantProfile/ns2:education/ns2:educationItem/ns2:courses/course">
            <tr>
              <td><xsl:value-of select="course_name"/></td>
              <td><xsl:value-of select="grade"/></td>
              <td><xsl:value-of select="ect"/></td>
              </tr>
              </xsl:for-each>
            <tr>
              <td>GPA:</td>
              <td colspan="2"><xsl:value-of select="ns2:applicantProfile/ns2:education/ns2:educationItem/ns2:gpa"/></td>
              </tr>
          </table></td>
        </tr>
    </table></td>
  </tr>
</table>
<p></p>
<table width="587" border="0">
  <tr>
    <td colspan="2"><span class="style1">Work Experience</span></td>
    </tr>
    <xsl:for-each select="ns2:applicantProfile/ns2:workExperience/ns2:work">
  <tr>
    <td width="108">Duration:</td>
    <td width="469"><xsl:value-of select="ns4:lengthofTime"/></td>
  </tr>
  <tr>
    <td>Company:</td>
    <td><xsl:value-of select="ns4:name"/></td>
  </tr>
  <tr>
    <td>Position:</td>
    <td><xsl:value-of select="ns4:positionorTitle"/></td>
  </tr>
  <tr>
    <td>Classification:</td>
    <td><xsl:value-of select="ns2:classification"/></td>
  </tr>
  <tr>
    <td>Region:</td>
    <td><xsl:value-of select="ns2:region"/></td>
  </tr>
  <tr>
  <td></td><td></td>
  </tr>
  </xsl:for-each>
</table>
</body>
</html>

</xsl:template>
</xsl:stylesheet>