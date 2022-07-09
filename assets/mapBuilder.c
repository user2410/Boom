/******************************************************************************

                            Online C Compiler.
                Code, Compile, Run and Debug C program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <stdio.h>

int readInt(FILE* fp){
    int res;
    for(int i=1; i<=4; i++){
        char* p = (char *)(&res)+4-i;
        fread(p, 1, 1, fp);
    }
    return res;
    
}

void writeInt(int x, FILE* fp){
    for(int i=1; i<=4; i++){
        char* p = (char *)(&x)+4-i;
        fwrite(p, 1, 1, fp);
    }
    // fwrite(&x, sizeof(int), 1, fp);
}

int main()
{
    FILE* dat = fopen("map01.dat", "wb");
    int arr[13][15] = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,1,3,3,3,1,0,0,0,1,3,3,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,1,3,3,3,1,0,0,0,1,3,3,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,3,0,0,2,0,0,0,2,0,0,3,1,0},
        {0,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };
    for(int i=0; i<13; i++){
        for(int j=0; j<15; j++) writeInt(arr[i][j], dat);
    }
    // player
    writeInt(0, dat); writeInt(0, dat);
    // 4 monster1
    writeInt(4, dat);
    writeInt(4, dat); writeInt(10, dat);
    writeInt(7, dat); writeInt(6, dat);
    writeInt(10, dat); writeInt(4, dat);
    writeInt(12, dat); writeInt(0, dat);
    // 3 monster2
    writeInt(3, dat);
    writeInt(3, dat); writeInt(3, dat);
    writeInt(6, dat); writeInt(11, dat);
    writeInt(10, dat); writeInt(14, dat);
    fclose(dat);
    
    dat = fopen("map01.dat", "rb");
    
    while(!feof(dat)){
        printf("%d;", readInt(dat));
    }
    
    fclose(dat);
    return 0;
}
