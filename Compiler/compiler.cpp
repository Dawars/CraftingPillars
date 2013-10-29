#include <iostream>
#include <fstream>
#include <cstdlib>
#include <string>
#include <vector>

using namespace std;

ifstream inputFile;
ofstream outputFile;

const int versionLength = 3;
int update;
int version[versionLength];
string versionString;
string mainFile = "../me/dawars/CraftingPillars/CraftingPillars.java";
string versionLine = "	public static final String version = ";

int main()
{
	inputFile.open("version.txt");
	for(int i = 0; i < versionLength; i++)
		inputFile >> version[i];
	inputFile.close();

	cout << "Please define the update's level! ";
	cin >> update;

	if(0 < update && update <= versionLength)
	{
		version[update-1]++;
		for(int i = update; i < versionLength; i++)
			version[i] = 0;
	}

	char buffer[1];
	for(int i = 0; i < versionLength; i++)
	{
		versionString += itoa(version[i], buffer, 10);
		versionString += '.';
	}
	versionString = versionString.substr(0, versionString.size()-1);

	cout << "The new version number is " << versionString << endl;

	vector<string> mainFileData;
	inputFile.open(mainFile.c_str());
	for(int i = 0; !inputFile.eof(); i++)
	{
		mainFileData.push_back("");
		getline(inputFile, mainFileData[mainFileData.size()-1]);
		if(mainFileData[mainFileData.size()-1].substr(0, versionLine.size()) == versionLine)
		{
			mainFileData[mainFileData.size()-1] = versionLine+"\""+versionString+"\";";
		}
		//cout << mainFileData[mainFileData.size()-1] << endl;
	}
	inputFile.close();

	/*for(int i = 0; i < mainFileData.size(); i++)
		cout << mainFileData[i] << endl;*/
	outputFile.open(mainFile.c_str());
	for(int i = 0; i < mainFileData.size(); i++)
		outputFile << mainFileData[i] << endl;
	outputFile.close();
	mainFileData.clear();

	outputFile.open("version.txt");
	for(int i = 0; i < versionLength; i++)
		outputFile << version[i] << endl;
	outputFile.close();

	string cmd = "compile.bat ";
	cmd += versionString;
	cout << "Calling cmd \"" << cmd << "\"" << endl;
	system(cmd.c_str());

	cout << "\nSuccesfully compiled version "+versionString+"!\n";
	cin >> cmd;
	return 0;
}
