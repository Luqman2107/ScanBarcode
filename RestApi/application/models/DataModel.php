<?php 
 
class DataModel extends CI_Model {
    public function insert($tabel, $arr)
    {
      $cek = $this->db->insert($tabel, $arr);
      return $cek;
    }
}