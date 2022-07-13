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
    // mxn map
    const int m=13;
    const int n=15;
    writeInt(m, dat); writeInt(n, dat);
    
    int arr[m][n] = {
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
    for(int i=0; i<m; i++){
        for(int j=0; j<n; j++) writeInt(arr[i][j], dat);
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
