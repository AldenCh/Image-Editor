#include <iostream>
#include <fstream>

#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION

#include "stb/stb_image.h"
#include "stb/stb_image_resize.h"
#include "stb/stb_image_write.h"

using namespace std;

struct MyImg {
    unsigned char* data;
    int width;
    int channels;
    int height;
    string filename;
};

void delete_img(MyImg** img)
{
    // Deletes img and sets it to NULL
    stbi_image_free((*img)->data);
    delete (*img);
    *img = 0;
}

//
// Image read/write
//
MyImg* load_jpeg_file(const string& filename)
{
    // Returns a MyImg pointer containing image data.
    // If the file loading is unsuccessful, it returns a 0 
    MyImg* img = new MyImg;
    (*img).data = stbi_load(filename.c_str(),&(*img).width,&(*img).height,&(*img).channels,0);
    if ((*img).data == 0){
        return 0;
    }
    else{
        return img;
    }
}

void save_to_jpeg_file(const string& filename, MyImg* img)
{
    // Saves to a jpeg file
    stbi_write_jpg(filename.c_str(),img->width,img->height,img->channels,img->data,100);
}

//
// Setters
//
void set_pixel_red(MyImg* img, int r, int c, uint8_t val)
{
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    *p = val;
}

void set_pixel_green(MyImg* img, int r, int c, uint8_t val)
{
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    *(p+1) = val;
}

void set_pixel_blue(MyImg* img, int r, int c, uint8_t val)
{
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    *(p+2) = val;
}

//
// Getters
//
uint8_t get_pixel_red(MyImg* img, int r, int c)
{
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    return *p;
}

uint8_t get_pixel_green(MyImg* img, int r, int c)
{
    
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    return *(p+1);
}

uint8_t get_pixel_blue(MyImg* img, int r, int c)
{
    unsigned char* p = img->data;
    p += r*img->width*img->channels;
    p += c*img->channels;
    return *(p+2);
}

int main(){
    // First line will always be the image name
    // Second line will be the name of the file to save to
    // Third line will be the number of changes
    // All lines after will be in the form:
    // - <row> <col> <red value> <green value> <blue value>

    string imageName = "";
    string newImageName = "";
    string numChanges;
    
    ifstream inputs("changes.txt");
    if (inputs.fail()){
        std::cout << "Error reading file" << endl;
        exit(1);
    }

    getline(inputs, imageName);
    getline(inputs, newImageName);
    getline(inputs, numChanges);

    MyImg* img = load_jpeg_file(imageName);

    if (stoi(numChanges) == 0){
        std::cout << "Saving Image..." << endl;
        if (newImageName != imageName){
            save_to_jpeg_file(newImageName, img);
        }
        delete_img(&img);
        inputs.close();
        
        std::cout << "Finished Saving." << endl;

        ofstream output("checkUpdate.txt");
        if (output.fail()){
            std::cout << "Error writing to file" << endl;
            exit(2);
        }
        output << "1";
        output.close();
        return 0;
    }
    
    int counter = 0;
    int pastPercent = 0;
    while(!inputs.eof()){
        string line;
        int row, col, r, g, b;
        int* arguments = new int[5];
        
        for (int i = 0; i < 5; i++){
            inputs >> line;
            if (line == ""){
                break;
            }
            arguments[i] = stoi(line);
        }

        if (line == ""){
            break;
        }

        set_pixel_red(img, arguments[1], arguments[0], (uint8_t)arguments[2]);
        set_pixel_green(img, arguments[1], arguments[0], (uint8_t)arguments[3]);
        set_pixel_blue(img, arguments[1], arguments[0], (uint8_t)arguments[4]);
        delete arguments;

        if (floor(((double)counter/stod(numChanges))*100) > (double)pastPercent){
            pastPercent++;
            if (pastPercent%10 == 0){
                std::cout << to_string(pastPercent) << "% Processed" << endl;
            }
        }
        counter++;
    }
    inputs.close();
    std::cout << "100% Processed" << endl;
    std::cout << "Finished Processing, Now Saving." << endl;

    save_to_jpeg_file(newImageName, img);
    for (int i = 0; i < newImageName.length(); i++){
        if (newImageName[i] == '/' || newImageName[i] == '\\'){
            save_to_jpeg_file("temp/"+newImageName.substr(i+1,newImageName.length()-i), img);
            break;
        }
    }

    delete_img(&img);

    ofstream output("checkUpdate.txt");
    if (output.fail()){
        std::cout << "Error writing to file" << endl;
        exit(2);
    }
    output << "1";
    output.close();

    std::cout << "Finished Saving." << endl;

    return 0;
}