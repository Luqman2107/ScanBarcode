<?php 
 
class DataModel extends CI_Model {

    public function index($id){
        return $this->db->get_where('barang' , ['id_barang' => $id])->result_array();
    }

    public function ori()
    {
        return $this->db->get_where('barang' , ['id_varian' => '1'])->result_array();
    }

    public function coklat()
    {
        return $this->db->get_where('barang' , ['id_varian' => '2'])->result_array();
    }

    public function box()
    {
        return $this->db->get_where('barang' , ['id_varian' => '0'])->result_array();
    }

}