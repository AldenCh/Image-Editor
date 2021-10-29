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