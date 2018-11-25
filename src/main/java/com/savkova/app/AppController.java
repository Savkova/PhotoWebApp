package com.savkova.app;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class AppController {

    private Map<Long, String> photos = new HashMap<>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile photo) {

        if (!photo.isEmpty()) {
            try {
                long id = System.currentTimeMillis();
                photos.put(id, id + "_" + photo.getOriginalFilename());

                Util.writeToFile(photos.get(id), photo.getBytes());

                model.addAttribute("photo_id", id);
                return "result";
            } catch (IOException e) {
                throw new PhotoErrorException();
            }
        } else throw new PhotoErrorException();
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {

        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {

        return photoById(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String onList(Model model) {

        model.addAttribute("photos", photos);

        return "list";
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        if (Util.deleteFile(id, photos) != null) {
            return "index";
        }
        throw new PhotoNotFoundException();
    }

    @RequestMapping(value = "/list/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> onCheckboxDelete(@RequestParam(value = "items_id[]", required = false) long[] itemsId) {
        if (itemsId != null && itemsId.length > 0) {
            for (long id : itemsId) {
               Util.deleteFile(id, photos);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/list/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> onDownload(@RequestParam("items_id[]") long[] itemsId) {

        if (itemsId == null)
            return null;

        File zipFile = Util.generateZipFile(itemsId, photos);
        byte[] bytes = null;
        try {
            bytes = FileUtils.readFileToByteArray(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(zipFile.length());
        headers.setContentType(MediaType.parseMediaType("application/zip"));
        headers.set("Content-Disposition", String.format("attachment; filename=\"%s\"", zipFile.getName()));

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = Util.readFromFile(photos.get(id));
        if (bytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }

        throw new PhotoNotFoundException();
    }

}
