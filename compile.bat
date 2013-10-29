@echo off

cd ".."
set VERSION=CraftingPillar\Compile-%DATE:~0,4%-%DATE:~5,2%-%DATE:~8,2%-%TIME:~0,2%-%TIME:~3,2%-%TIME:~6,2%
echo Compiling to %VERSION%

xcopy "CraftingPillar\me" "MCP 1.6.4\mcp\src\minecraft\me\" /S /Y
cd "MCP 1.6.4\mcp"
runtime\bin\python\python_mcp runtime\recompile.py %*
runtime\bin\python\python_mcp runtime\reobfuscate.py %*
cd "..\.."
del "MCP 1.6.4\mcp\src\minecraft\me\dawars" /F /Q /S

xcopy "MCP 1.6.4\mcp\reobf\minecraft\me" "%VERSION%\me\" /S /Y
xcopy "CraftingPillar\assets" "%VERSION%\assets\" /S /Y

pause