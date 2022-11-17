set name=WFSApp

cd .\%name%
"C:\Program Files (x86)\WinRAR\winrar.exe" a -r -afzip .\%name%.zip .\*
cd ..
rename .\%name%\%name%.zip %name%.war
del /q D:\Study\FullStackCourse\glassfish6\glassfish\domains\domain1\autodeploy\%name%.war
del /q D:\Study\FullStackCourse\glassfish6\glassfish\domains\domain1\autodeploy\%name%.war_deployed
del /q D:\Study\FullStackCourse\glassfish6\glassfish\domains\domain1\autodeploy\%name%.war_deployFailed
move .\%name%\%name%.war D:\Study\FullStackCourse\glassfish6\glassfish\domains\domain1\autodeploy
del /q .\%name%\%name%.war
del /q .\%name%\%name%.zip
del /q /s .\%name%\WEB-INF\classes\rest\*.class 
pause